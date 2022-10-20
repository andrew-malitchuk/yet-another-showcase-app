package io.yasa.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import io.yasa.domain.datasource.BreweriesRemoteDataSource
import io.yasa.domain.usecase.BreweriesUseCase
import io.yasa.models.data.mapper.BreweryDomainUiMapper
import io.yasa.models.data.model.BreweryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import logcat.logcat

class ListViewModel(
    private val breweriesUseCase: BreweriesUseCase,
    private val breweriesRemoteDataSource: BreweriesRemoteDataSource
) : ViewModel() {

    private val uiMapper: BreweryDomainUiMapper = BreweryDomainUiMapper()

    private val _searchFlow: MutableStateFlow<List<BreweryUiModel>?> = MutableStateFlow(null)
    val searchFlow = _searchFlow.asStateFlow()

    private val _sortFlowQeaury: MutableStateFlow<Pair<SortField, Order>?> = MutableStateFlow(
        Pair(
            SortField.NAME, Order.ASC
        )
    )
    val sortFlow = _sortFlowQeaury.asStateFlow()


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
//        return breweriesRemoteDataSource.getBreweries()
        logcat("_sortFlowQeaury") { "${_sortFlowQeaury.value}" }
        return breweriesRemoteDataSource.getBreweries(_sortFlowQeaury.value?.toQuery())
            .map { pagingData ->
                pagingData.map { domainModel ->
                    uiMapper.mapTo(domainModel)
                }
            }/*.cachedIn(viewModelScope)*/.flowOn(Dispatchers.IO)
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

    fun sort(field: SortField? = null, order: Order? = null) {
        var previousValue = _sortFlowQeaury.value
        field?.let {
            previousValue = previousValue?.copy(first = it)
        }
        order?.let {
            previousValue = previousValue?.copy(second = it)
        }
        logcat("sort") { "${previousValue?.first} | ${previousValue?.second}" }

        this.getBreweries()

        _sortFlowQeaury.update {
            previousValue
        }
    }


    enum class SortField {
        NAME, TYPE, DATE, NI
    }

    enum class Order {
        ASC, DESC, NI
    }

    fun Pair<SortField, Order>.toQuery(): String {
        return "${this.first}:${this.second}"
    }

}