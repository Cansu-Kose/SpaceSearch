package com.example.spacesearch.ui.component.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.spacesearch.R
import com.example.spacesearch.data.model.entity.ChildData
import com.example.spacesearch.ui.component.SearchBar
import com.example.spacesearch.ui.theme.Green
import com.example.spacesearch.viewmodel.MainViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val searchResults by viewModel.search.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var searchText by remember  { mutableStateOf(TextFieldValue("")) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(searchText) { newText ->
            searchText = newText
            viewModel.search(newText.text, "week", 20)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            searchText.text.isEmpty() -> {
                DefaultSearchState()
            }
            isLoading -> {
                CircularProgressIndicator()
            }
            searchResults.isNullOrEmpty() -> {
                NoResultFoundState()
            }
            else -> {
                SearchResultsState(searchResults!!)
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
fun SearchResultsState(results: List<ChildData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(results) { result ->
            CoilImage(
                imageModel = { result.data.url },
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                },
                failure = {
                    Text(
                        text = "Image failed to load",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            )
        }
    }
}
