package io.yasa.navigation

sealed class NavigationFlow {
    object ListFlow : NavigationFlow()
    class DetailsFlow(val id: String) : NavigationFlow()
    object SearchFlow : NavigationFlow()
}