package io.yasa.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import io.yasa.main.databinding.ActivityMainBinding
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.Navigator
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.platform.network.ConnectivityLiveData
import io.yasa.ui.viewbinding.viewBinding
import logcat.logcat
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.subKodein


class MainActivity : AppCompatActivity(R.layout.activity_main), ToFlowNavigatable, KodeinAware {

    private val navigator: Navigator = Navigator()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    private val checkConnection by lazy { ConnectivityLiveData(application) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            val navHostFragment = if (isPortrait(this@MainActivity)) {
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            } else {
                supportFragmentManager.findFragmentById(R.id.nav_details_fragment) as NavHostFragment
            }
            val navController = navHostFragment.navController
            navigator.navController = navController
        }
        checkConnection.observe(this) { isConnected ->
            logcat { "isConnected: $isConnected" }

            if (!isConnected) {
                viewBinding.tvError?.apply {
//                    isVisible = true
                    isActivated = false
                }
            } else {
                viewBinding.tvError?.apply {
//                    lifecycleScope.launch(Dispatchers.Main) {
//                        delay(3000)
//                    isActivated = true
//                        delay(3000)
                        isVisible = false
//                    }
                }
            }

            viewBinding.tvError?.isVisible = !isConnected

        }
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }

    override val kodein: Kodein by subKodein(closestKodein(), allowSilentOverride = true) {
//        bind<LifecycleOwner>(overrides = true) with provider { this@MainActivity }
//        bind<Context>(overrides = true) with provider { this@MainActivity }
//		bind<Activity>() with provider { this@BaseActivity }
    }

    fun isPortrait(context: Context): Boolean {
        return context.resources.getBoolean(io.yasa.ui.R.bool.is_portrait)
    }

}