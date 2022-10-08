package io.yasa.app

import android.app.Application
import io.yasa.database.impl.di.databaseModule
import io.yasa.domain.di.domainModule
import io.yasa.list.di.listModule
import io.yasa.network.impl.di.networkModule
import io.yasa.repository.impl.di.repositoryModule
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class YasaApplication : Application(), KodeinAware {

    override val kodein: Kodein by Kodein.lazy {
        importAll(*networkModule)
        importAll(*databaseModule)
        importAll(*repositoryModule)
        importAll(domainModule)
        importAll(listModule)
        import(androidXModule(this@YasaApplication))
    }

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }

}