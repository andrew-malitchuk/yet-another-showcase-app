package io.yasa.app

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class YasaApplication : Application(), KodeinAware {
    override val kodein: Kodein by Kodein.lazy {

    }
}