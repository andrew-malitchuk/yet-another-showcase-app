package io.yasa.network.source

import io.yasa.network.model.BreweryNetModel

interface BreweriesNetSource {
    suspend fun getBrewery(id: String): BreweryNetModel
    suspend fun getBreweries(page: Int, perPage: Int): List<BreweryNetModel>
    suspend fun getBreweries(page: Int, perPage: Int, sort: String? = null): List<BreweryNetModel>
    suspend fun getBreweries(
        page: Int,
        perPage: Int,
        sort: String? = null,
        filter: Triple<String?,String?,String?>? = null
    ): List<BreweryNetModel>

    suspend fun search(query: String): List<BreweryNetModel>
}