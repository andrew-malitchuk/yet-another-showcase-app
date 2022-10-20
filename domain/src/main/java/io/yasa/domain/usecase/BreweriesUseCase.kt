package io.yasa.domain.usecase

import io.yasa.domain.mapper.BreweryRepoDomainMapper
import io.yasa.domain.model.BreweryDomainModel
import io.yasa.repository.BreweriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BreweriesUseCase(
    private val breweriesRepository: BreweriesRepository
) : BaseUseCase() {

    private val repoDomainMapper = BreweryRepoDomainMapper()

    val breweriesStateFlow: Flow<List<BreweryDomainModel>> =
        breweriesRepository.observeBreweriesChanges().map { repoList ->
            repoList.map { repoItem ->
                repoDomainMapper.mapTo(repoItem)
            }
        }

    suspend fun getBrewery(id: String): BreweryDomainModel {
        return breweriesRepository.getBrewery(id).let { repoModel ->
            repoDomainMapper.mapTo(repoModel)
        }
    }

    //    suspend fun getAndSaveBreweries(page: Int): List<BreweryDomainModel> {
    suspend fun getAndSaveBreweries(page: Int, sort: String? = null): List<BreweryDomainModel> {
//        return breweriesRepository.getAndSaveBreweries(page, PER_PAGE_ITEMS).map { repoModel ->
        return breweriesRepository.getAndSaveBreweries(page, PER_PAGE_ITEMS, sort)
            .map { repoModel ->
                repoDomainMapper.mapTo(repoModel)
            }
    }

    suspend fun getBreweries(page: Int): List<BreweryDomainModel> {
        return breweriesRepository.getBreweries(page, PER_PAGE_ITEMS).map { repoModel ->
            repoDomainMapper.mapTo(repoModel)
        }
    }

    suspend fun refreshBreweries(page: Int) {
        breweriesRepository.refreshBreweries(page, PER_PAGE_ITEMS)
    }

    suspend fun search(query: String): List<BreweryDomainModel> {
        return breweriesRepository.search(query).map { repoModel ->
            repoDomainMapper.mapTo(repoModel)
        }
    }

    companion object {
        const val PER_PAGE_ITEMS = 10
    }

}