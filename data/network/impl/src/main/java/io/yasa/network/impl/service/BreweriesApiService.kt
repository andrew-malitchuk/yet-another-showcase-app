package io.yasa.network.impl.service

import io.yasa.network.model.BreweryNetModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreweriesApiService {

    @GET("https://api.openbrewerydb.org/breweries/{id}")
    suspend fun getBrewery(
        @Path("id") id: String
    ): BreweryNetModel

    @GET("https://api.openbrewerydb.org/breweries")
    suspend fun getBreweries(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("sort") sort: String? = null,
    ): List<BreweryNetModel>

    @GET("https://api.openbrewerydb.org/breweries")
    suspend fun getBreweries(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("sort") sort: String? = null,
        @Query("by_city") city: String? = null,
        @Query("by_state") state: String? = null,
        @Query("by_type") type: String? = null,
    ): List<BreweryNetModel>

    @GET("https://api.openbrewerydb.org/breweries/search")
    suspend fun search(@Query("query") query: String): List<BreweryNetModel>

}
