package io.yasa.network.impl.source

import io.yasa.network.impl.service.BreweriesApiService
import io.yasa.network.model.BreweryNetModel
import io.yasa.network.source.BreweriesNetSource

class BreweriesNetSourceImpl(
    apiService: BreweriesApiService
) : BaseNetSource<BreweriesApiService>(apiService), BreweriesNetSource {

    override suspend fun getBrewery(id: String): BreweryNetModel = launch {
        getBrewery(id)
    }

    override suspend fun getBreweries(page: Int, perPage: Int): List<BreweryNetModel> = launch {
        getBreweries(page, perPage)
    }

    override suspend fun getBreweries(
        page: Int,
        perPage: Int,
        sort: String?
    ): List<BreweryNetModel> = launch {
//        getBreweries(page, perPage)
        getBreweries(page, perPage, sort)
    }

    override suspend fun getBreweries(
        page: Int, perPage: Int, sort: String?, filter: Triple<String?, String?, String?>?
    ): List<BreweryNetModel> = launch {
//        getBreweries(page, perPage)
        getBreweries(page, perPage, sort, filter?.first, filter?.second, filter?.third)
    }

    override suspend fun search(query: String): List<BreweryNetModel> = launch {
        search(query)
    }

}