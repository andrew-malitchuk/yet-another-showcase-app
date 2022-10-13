package io.yasa.platform

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import io.yasa.navigation.NavigationFlow
import io.yasa.navigation.Navigator
import io.yasa.navigation.ToFlowNavigatable
import io.yasa.platform.network.ConnectivityLiveData
import logcat.logcat
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.subKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

open class BaseActivity(layout: Int) : AppCompatActivity(layout), ToFlowNavigatable,
    KodeinAware {

    override val kodein: Kodein by subKodein(closestKodein(), allowSilentOverride = true) {
        bind<LifecycleOwner>(overrides = true) with provider { this@BaseActivity }
        bind<Context>(overrides = true) with provider { this@BaseActivity }
    }

    private val navigator: Navigator = Navigator()

    private val checkConnection by lazy { ConnectivityLiveData(application) }


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        checkConnection.observe(this) {
            if (it) {

            }
        }
    }

    protected fun onConnectivityChanged(isConnected: Boolean) {
        logcat { "isConnected: $isConnected" }

    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }


}