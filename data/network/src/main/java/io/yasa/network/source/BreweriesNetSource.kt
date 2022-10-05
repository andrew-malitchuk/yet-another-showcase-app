package io.yasa.network.source

import io.yasa.network.model.BreweryNetModel

interface BreweriesNetSource {
    suspend fun getBrewery(id:String): BreweryNetModel
    suspend fun getBreweries(page:Int,perPage:Int):List<BreweryNetModel>
}