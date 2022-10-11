package io.yasa.domain.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.yasa.domain.model.BreweryDomainModel
import io.yasa.domain.usecase.BreweriesUseCase
import io.yasa.repository.BreweriesRepository
import kotlinx.coroutines.flow.Flow

class BreweriesRemoteDataSourceImp(
    private val breweriesUseCase: BreweriesUseCase,
    private val breweriesRepository: BreweriesRepository
) : BreweriesRemoteDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreweries(): Flow<PagingData<BreweryDomainModel>> {
        return Pager(
            pagingSourceFactory = {
                BreweriesPagingSource(
                    breweriesUseCase
                )
            },
            config = PagingConfig(
                10
            ),
            remoteMediator = BreweriesMediator(
                breweriesRepository
            )
        ).flow
    }

}