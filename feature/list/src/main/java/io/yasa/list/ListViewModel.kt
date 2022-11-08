package io.yasa.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import io.yasa.domain.datasource.BreweriesRemoteDataSource
import io.yasa.domain.usecase.BreweriesUseCase
import io.yasa.models.data.mapper.BreweryDomainUiMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import logcat.logcat

class ListViewModel(
    private val breweriesUseCase: BreweriesUseCase,
    private val breweriesRemoteDataSource: BreweriesRemoteDataSource
) : ViewModel() {

    private val uiMapper: BreweryDomainUiMapper = BreweryDomainUiMapper()

    private val _sortFlowQeaury: MutableStateFlow<Pair<SortField, Order>?> = MutableStateFlow(
        Pair(
            SortField.NAME, Order.ASC
        )
    )

    var foo: QueryParams? = null

    private var searchJob: Job? = null
    fun search(query: String?) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            viewModelScope.launch {
                val foo = fooSortFlow.value?.copy(search = query)
                fooSortFlow.emit(foo)
            }
        }
    }

    private var filterJob: Job? = null
    fun filter(city: String? = null, state: String? = null, type: String? = null) {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            delay(500)

            val prevFilter = fooSortFlow.value?.filter
            city?.let {
                prevFilter?.city = it
            }
            state?.let {
                prevFilter?.state = it
            }
            type?.let {
                prevFilter?.type = it
            }


            viewModelScope.launch {
                foo = fooSortFlow.value?.copy(filter = prevFilter)
                fooSortFlow.tryEmit(foo)

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

        viewModelScope.launch {
            _sortFlowQeaury.emit(previousValue)
            foo = fooSortFlow.value?.copy(sort = previousValue)
            fooSortFlow.emit(foo)
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

    val fooSortFlow: MutableStateFlow<QueryParams?> = MutableStateFlow(QueryParams())

    @OptIn(ExperimentalCoroutinesApi::class)
    val fooData = fooSortFlow.flatMapLatest {
        var sort = it?.sort
        var search = it?.search
        var filter = it?.filter

        breweriesRemoteDataSource.getBreweries(
            sort?.toQuery(),
            search,
            Triple(filter?.city, filter?.state, filter?.type)
        ).map { pagingData ->
            pagingData.map { domainModel ->
                uiMapper.mapTo(domainModel)
            }
        }
    }

    data class QueryParams(
        var sort: Pair<SortField, Order>? = null,
        var search: String? = null,
        // city, state, type
        var filter: Filter? = Filter()
    )

    data class Filter(
        var city: String? = null,
        var state: String? = null,
        var type: String? = null,
    )

}