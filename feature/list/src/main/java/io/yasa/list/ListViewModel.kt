package io.yasa.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.yasa.domain.usecase.BreweriesUseCase
import io.yasa.models.data.mapper.BreweryDomainUiMapper
import io.yasa.models.data.model.BreweryUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ListViewModel(
    private val breweriesUseCase: BreweriesUseCase
) : ViewModel() {

    private val uiMapper: BreweryDomainUiMapper = BreweryDomainUiMapper()


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


}