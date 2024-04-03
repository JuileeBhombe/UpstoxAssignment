package com.juileebhombe.upstoxassignment.ui.base

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juileebhombe.upstoxassignment.ui.holdings.HoldingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.HOLDINGS
    ) {
        composable(route = Routes.HOLDINGS) {
            HoldingsScreen()
        }
    }
}