package com.example.spacesearch.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spacesearch.ui.component.screens.mainscreen.DetailScreen
import com.example.spacesearch.ui.component.screens.mainscreen.MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable(
            "detail/{title}/{subreddit}/{imageUrl}/{timestamp}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("subreddit") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("timestamp") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = Uri.decode(backStackEntry.arguments?.getString("title") ?: "")
            val subreddit = Uri.decode(backStackEntry.arguments?.getString("subreddit") ?: "")
            val imageUrl = Uri.decode(backStackEntry.arguments?.getString("imageUrl") ?: "")
            val timestamp = backStackEntry.arguments?.getString("timestamp")?.toDoubleOrNull() ?: 0.0

            DetailScreen(title, subreddit, imageUrl, timestamp)
        }
    }
}
