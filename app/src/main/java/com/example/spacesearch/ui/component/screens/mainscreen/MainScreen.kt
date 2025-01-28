package com.example.spacesearch.ui.component.screens.mainscreen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.spacesearch.R
import com.example.spacesearch.data.model.entity.PostData
import com.example.spacesearch.ui.component.SearchBar
import com.example.spacesearch.ui.theme.Green
import com.example.spacesearch.viewmodel.MainViewModel
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()

    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    val tParameter: String = remoteConfig.getString("t_parameter")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        SearchBar(
            searchText = searchText,
            onSearch = { newText ->
                if(searchText.text != newText.text) {
                    searchText = newText

                    if (newText.text.isNotEmpty()) {

                        viewModel.search(newText.text, tParameter, 20)
                    }
                }
            }
        )

        when {
            searchText.text.isEmpty() -> {
                DefaultSearchState()
            }
            isLoading && searchResults.isEmpty() -> {
                CircularProgressIndicator()
            }
            searchResults.isEmpty() -> {
                NoResultFoundState()
            }
            else -> {
                SearchResultsState(
                    results = searchResults,
                    navController = navController,
                    isLoading = isLoading,
                    onLoadMore = { viewModel.loadNextPage() }
                )
            }
        }
    }
}

@Composable
fun DefaultSearchState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = "Search Icon",
            modifier = Modifier.size(64.dp),
            tint = Green
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Please type to search",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun NoResultFoundState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.rectangle_logo),
            contentDescription = "No Results",
            modifier = Modifier.size(64.dp),
            tint = Green
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No result found",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun SearchResultsState(
    results: List<PostData>,
    navController: NavController,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyGridState()

    // If we scrolled to the last item -> load more
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisible != null && lastVisible >= results.size - 1
        }
    }

    // Whenever shouldLoadMore changes to true, load next page if not already loading
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value && !isLoading) {
            onLoadMore()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(results) { result ->
            CoilImage(
                imageModel = { result.url ?: ""},
                modifier = Modifier
                    .clickable {
                        val subreddit:String= result.subredditNamePrefixed ?: ""
                        val url:String = result.url ?: ""
                        val title:String = result.title ?: ""
                        val createdUtc:String = result.createdUtc.toString()
                        navController.navigate(
                            "detail/${Uri.encode(title)}/${Uri.encode(subreddit)}/${Uri.encode(url)}/${createdUtc}"
                        )
                    }
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                failure = {
                    Image(
                        painter = painterResource(id = R.drawable.splash_logo),
                        contentDescription = "Default Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            )
        }

        if (isLoading) {
            item(span = { GridItemSpan(3) }) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
