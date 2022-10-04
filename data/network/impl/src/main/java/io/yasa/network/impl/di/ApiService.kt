package io.yasa.network.impl.di

import io.yasa.network.impl.service.BreweriesApiService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

internal val apiServiceModule = Kodein.Module("apiServiceModule") {

    bind<BreweriesApiService>() with singleton {
        instance<Retrofit>().create(BreweriesApiService::class.java)
    }

}
