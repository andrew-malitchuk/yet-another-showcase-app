package io.yasa.domain.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.yasa.domain.model.BreweryDomainModel
import io.yasa.repository.BreweriesRepository

@OptIn(ExperimentalPagingApi::class)
class BreweriesMediator(val repository: BreweriesRepository) :
    RemoteMediator<Int, BreweryDomainModel>() {

    private var page = 1
    private var endOfPaginationReached = false

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreweryDomainModel>,
    ): MediatorResult {

        page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                state.lastItemOrNull() ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                page + 1
            }
        }

        return try {
            repository.refreshBreweries(page, 10)
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}