package io.yasa.network.impl.source

import io.yasa.network.impl.service.BreweriesApiService
import io.yasa.network.source.BreweriesNetSource

class BreweriesNetSourceImpl(
    apiService: BreweriesApiService
) : BaseNetSource<BreweriesApiService>(apiService), BreweriesNetSource {

    override suspend fun getBrewery(id: String): BreweriesNetSource = launch {
        getBrewery(id)
    }

    override suspend fun getBreweries(page: Int, perPage: Int): List<BreweriesNetSource> = launch {
        getBreweries(page, perPage)
    }

}