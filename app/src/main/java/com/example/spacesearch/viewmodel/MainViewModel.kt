package com.example.spacesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacesearch.data.common.DataState
import com.example.spacesearch.data.model.entity.ChildData
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
class MainViewModel @Inject constructor(private val repo: SearchRepository):ViewModel() {

    private val _search = MutableStateFlow<List<ChildData>?>(null)
    val search: StateFlow<List<ChildData>?> get ()= _search.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()


    fun search(keyword: String, time: String, limit: Int) {
        viewModelScope.launch {
            repo.getTopPosts(keyword,time,limit).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _search.value = it.data[0].data.children
                        _isLoading.value = false
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

}