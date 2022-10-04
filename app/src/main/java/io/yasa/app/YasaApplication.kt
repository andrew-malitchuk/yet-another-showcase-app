package io.yasa.app

import android.app.Application
import io.yasa.network.impl.di.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class YasaApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {
        importAll(*networkModule)
    }

}