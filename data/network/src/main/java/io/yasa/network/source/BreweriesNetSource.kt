package io.yasa.network.source

interface BreweriesNetSource {
    suspend fun getBrewery(id:String):BreweriesNetSource
    suspend fun getBreweries(page:Int,perPage:Int):List<BreweriesNetSource>
}