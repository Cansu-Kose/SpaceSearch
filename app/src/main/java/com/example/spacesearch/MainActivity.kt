package com.example.spacesearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.spacesearch.navigation.AppNavigation
import com.example.spacesearch.ui.theme.SpaceSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            SpaceSearchTheme {
                AppNavigation()
            }
        }

    }
}
