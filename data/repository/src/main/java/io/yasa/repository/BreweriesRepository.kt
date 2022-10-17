package io.yasa.repository

import io.yasa.repository.model.BreweryRepoModel
import kotlinx.coroutines.flow.Flow

interface BreweriesRepository {
    suspend fun getBrewery(id:String):BreweryRepoModel
    suspend fun getAndSaveBreweries(page: Int, perPage: Int):List<BreweryRepoModel>
    suspend fun getBreweries(page: Int, perPage: Int):List<BreweryRepoModel>
    suspend fun refreshBreweries(page:Int, perPage:Int)
    fun observeBreweriesChanges(): Flow<List<BreweryRepoModel>>
    suspend fun search(query:String): List<BreweryRepoModel>
}