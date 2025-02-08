package com.jer.banyumastourismapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.jer.banyumastourismapp.presentation.navigator.CoreNavigator

@Composable
fun NavGraph(startDestination: String) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.HomeScreen.route
        ) {
            composable(Route.HomeScreen.route) {
                CoreNavigator()
            }
        }

    }

}