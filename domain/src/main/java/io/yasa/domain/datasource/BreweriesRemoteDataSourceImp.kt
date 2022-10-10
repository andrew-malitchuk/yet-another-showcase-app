package io.yasa.domain.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.yasa.domain.model.BreweryDomainModel
import io.yasa.domain.usecase.BreweriesUseCase
import kotlinx.coroutines.flow.Flow

class BreweriesRemoteDataSourceImp(
    private val breweriesUseCase: BreweriesUseCase
) : BreweriesRemoteDataSource {

    override fun getBreweries(): Flow<PagingData<BreweryDomainModel>> {
        return Pager(
            pagingSourceFactory = {
                BreweriesPagingSource(
                    breweriesUseCase
                )
            },
            config = PagingConfig(
                10
            )
        ).flow
    }

}