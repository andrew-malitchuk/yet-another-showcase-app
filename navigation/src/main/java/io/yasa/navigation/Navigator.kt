package io.yasa.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
//        NavigationFlow.DetailsFlow -> navController.navigate(MainNavGraphDirections.actionDetailsFlow())
//        NavigationFlow.ListFlow -> navController.navigate(MainNavGraphDirections.actionListFlow())
//        NavigationFlow.SearchFlow -> navController.navigate(MainNavGraphDirections.actionSearchFlow())
        else -> {}
    }
}