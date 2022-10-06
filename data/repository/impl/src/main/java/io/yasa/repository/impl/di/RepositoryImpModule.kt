package io.yasa.repository.impl.di

import io.yasa.repository.BreweriesRepository
import io.yasa.repository.impl.BreweriesRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val repositoryImplModule = Kodein.Module("repositoryImplModule") {
    bind<BreweriesRepository>() with provider {
        BreweriesRepositoryImpl(
            instance(),
            instance(),
            instance(),
            instance(),
            instance(),
        )
    }

}
