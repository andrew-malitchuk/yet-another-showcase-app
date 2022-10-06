package io.yasa.database.impl.di

import io.yasa.database.impl.source.BreweriesDbSourceImpl
import io.yasa.database.source.BreweriesDbSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

internal val dbSourceModule = Kodein.Module("dbSourceModule") {
    bind<BreweriesDbSource>() with provider {
        BreweriesDbSourceImpl(
            instance()
        )
    }
}
