package io.yasa.domain.datasource

import androidx.paging.PagingData
import io.yasa.domain.model.BreweryDomainModel
import kotlinx.coroutines.flow.Flow

interface BreweriesRemoteDataSource {
    fun getBreweries(): Flow<PagingData<BreweryDomainModel>>
    fun getBreweries(sort: String?): Flow<PagingData<BreweryDomainModel>>
    fun getBreweries(sort: String?,search:String?): Flow<PagingData<BreweryDomainModel>>
}
