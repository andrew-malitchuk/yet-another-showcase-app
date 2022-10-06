package io.yasa.database.source

import io.yasa.database.model.BreweryDbModel
import kotlinx.coroutines.flow.Flow

interface BreweriesDbSource {
    suspend fun getBrewery(id:String): BreweryDbModel
    suspend fun getBreweries(page:Int,perPage:Int):List<BreweryDbModel>
    suspend fun replaceAll(list:List<BreweryDbModel>)
    suspend fun addOrReplace(list: List<BreweryDbModel>)
    fun observeChanges(): Flow<List<BreweryDbModel>>
}