package com.example.spacesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.PostData
import com.example.spacesearch.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: SearchRepository
) : ViewModel() {

    // All the items loaded so far
    private val _searchResults = MutableStateFlow<List<PostData>>(emptyList())
    val searchResults: StateFlow<List<PostData>> = _searchResults.asStateFlow()

    // The next page token from the API
    private val _after = MutableStateFlow<String?>(null)

    // Loading indicator
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Keep track of the current search so we can load more pages
    private var currentKeyword: String = ""
    private var currentTime: String = ""

    /**
     * If after == null => new search (replace old data).
     * If after != null => load more data (append).
     */
    fun search(
        keyword: String,
        time: String,
        limit: Int = 20
    ) {
        currentKeyword = keyword
        currentTime = time

        viewModelScope.launch {
            repo.getTopPosts(keyword, time, limit, _after.value ?: "null")
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Loading -> {
                            _isLoading.value = true
                        }
                        is DataState.Success -> {
                            val result = dataState.data
                            val newPosts = result.posts
                            val newAfter = result.after

                            if (_after.value == null) {
                                // Fresh search => Replace old list
                                _searchResults.value = newPosts
                            } else {
                                // Append
                                _searchResults.value += newPosts
                            }

                            // Update `_after`
                            _after.value = newAfter
                            _isLoading.value = false
                        }
                        is DataState.Error -> {
                            // Handle error
                            _isLoading.value = false
                        }
                    }
                }
                .launchIn(this)
        }
    }

    /**
     * Load the next page (only if `after` is non-null).
     */
    fun loadNextPage() {
        // If we have no next page or are already loading, do nothing
        if (_after.value == null || _isLoading.value) return

        _after.value = "null"
    }
}
