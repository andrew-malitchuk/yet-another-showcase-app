package io.yasa.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import io.yasa.domain.datasource.BreweriesRemoteDataSource
import io.yasa.domain.usecase.BreweriesUseCase
import io.yasa.models.data.mapper.BreweryDomainUiMapper
import io.yasa.models.data.model.BreweryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val breweriesUseCase: BreweriesUseCase,
    private val breweriesRemoteDataSource: BreweriesRemoteDataSource
) : ViewModel() {

    private val uiMapper: BreweryDomainUiMapper = BreweryDomainUiMapper()

    private val _searchFlow: MutableStateFlow<List<BreweryUiModel>?> = MutableStateFlow(null)
    val searchFlow = _searchFlow.asStateFlow()

    val breweriesStateFlow: Flow<List<BreweryUiModel>> =
        breweriesUseCase.breweriesStateFlow.map { domainList ->
            domainList.map { domainItem ->
                uiMapper.mapTo(domainItem)
            }
        }

    suspend fun getBrewery(id: String): BreweryUiModel {
        return breweriesUseCase.getBrewery(id).let { domainModel ->
            uiMapper.mapTo(domainModel)
        }
    }

    fun getAndSaveBreweries(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            breweriesUseCase.getAndSaveBreweries(page)
        }
    }

    suspend fun refreshBreweries() {
        breweriesUseCase.refreshBreweries(1)
    }

    fun getBreweries(): Flow<PagingData<BreweryUiModel>> {
        return breweriesRemoteDataSource.getBreweries()
            .map { pagingData ->
                pagingData.map { domainModel ->
                    uiMapper.mapTo(domainModel)
                }
            }.cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun search(query: String) {
        viewModelScope.launch {
            val searchResult = breweriesUseCase.search(query).map { domainModule ->
                uiMapper.mapTo(domainModule)
            }
            _searchFlow.update {
                searchResult
            }
        }
    }

}