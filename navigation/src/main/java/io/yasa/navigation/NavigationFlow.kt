package io.yasa.navigation

sealed class NavigationFlow {
    object ListFlow : NavigationFlow()
    object DetailsFlow : NavigationFlow()
    object SearchFlow : NavigationFlow()
}