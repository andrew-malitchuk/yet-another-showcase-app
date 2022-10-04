package io.yasa.network.impl.service

import io.yasa.network.source.BreweriesNetSource
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweriesApiService {

    @GET("https://api.openbrewerydb.org/breweries/{id}")
    suspend fun getBrewery(
        @Path("id") id: String
    ): BreweriesNetSource

    @GET("https://api.openbrewerydb.org/breweries")
    suspend fun getBreweries(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): List<BreweriesNetSource>

}
