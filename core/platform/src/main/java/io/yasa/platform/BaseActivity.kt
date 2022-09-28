package io.yasa.platform

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.subKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class BaseActivity : AppCompatActivity(),
    KodeinAware {
    override val kodein: Kodein by subKodein(closestKodein(), allowSilentOverride = true) {
        bind<LifecycleOwner>(overrides = true) with provider { this@BaseActivity }
        bind<Context>(overrides = true) with provider { this@BaseActivity }
    }


}