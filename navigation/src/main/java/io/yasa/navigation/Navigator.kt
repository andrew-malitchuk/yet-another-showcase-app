package io.yasa.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        is NavigationFlow.DetailsFlow -> navController.navigate(
            MainNavGraphDirections.actionDetailsFlow(
                navigationFlow.id
            )
        )
        else -> {}
    }
}