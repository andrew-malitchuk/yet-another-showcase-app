package io.yasa.network.impl.di

import io.yasa.network.impl.source.BreweriesNetSourceImpl
import io.yasa.network.source.BreweriesNetSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

internal val netSourceModule = Kodein.Module("netSourceModule") {

    bind<BreweriesNetSource>() with provider {
        BreweriesNetSourceImpl(
            instance()
        )
    }

}
