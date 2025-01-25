package com.example.spacesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.spacesearch.ui.component.SearchBar
import com.example.spacesearch.ui.theme.SpaceSearchTheme
import com.example.spacesearch.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            SpaceSearchTheme {
                SearchBar(viewModel)
            }
        }

    }
}
