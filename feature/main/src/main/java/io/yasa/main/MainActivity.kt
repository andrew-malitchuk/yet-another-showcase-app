package io.yasa.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.yasa.main.databinding.ActivityMainBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.Navigator
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.ui.viewbinding.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main), ToFlowNavigatable {

    private val navigator: Navigator = Navigator()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBinding) {
//            val navController = findNavController(R.id.nav_host_fragment)
//            navigator.navController = navController
        }
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}