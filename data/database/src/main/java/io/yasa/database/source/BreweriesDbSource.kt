package io.yasa.database.source

import io.yasa.database.model.BreweryDbModel

interface BreweriesDbSource {
    suspend fun getBrewery(id:String): BreweryDbModel
    suspend fun getBreweries(page:Int,perPage:Int):List<BreweryDbModel>
}