package io.yasa.repository.impl.di

import io.yasa.repository.impl.mapper.BreweryDbRepoMapper
import io.yasa.repository.impl.mapper.BreweryNetDbMapper
import io.yasa.repository.impl.mapper.BreweryNetRepoMapper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

internal val repoMapperModule = Kodein.Module("repoMapperModule") {

    bind<BreweryDbRepoMapper>() with provider {
        BreweryDbRepoMapper()
    }

    bind<BreweryNetDbMapper>() with provider {
        BreweryNetDbMapper()
    }

    bind<BreweryNetRepoMapper>() with provider {
        BreweryNetRepoMapper()
    }

}