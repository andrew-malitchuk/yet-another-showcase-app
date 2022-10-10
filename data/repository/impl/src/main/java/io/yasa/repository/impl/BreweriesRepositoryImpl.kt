package io.yasa.repository.impl

import io.yasa.database.source.BreweriesDbSource
import io.yasa.network.source.BreweriesNetSource
import io.yasa.repository.BreweriesRepository
import io.yasa.repository.impl.mapper.BreweryDbRepoMapper
import io.yasa.repository.impl.mapper.BreweryNetDbMapper
import io.yasa.repository.impl.mapper.BreweryNetRepoMapper
import io.yasa.repository.model.BreweryRepoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BreweriesRepositoryImpl(
    private val breweriesNetSource: BreweriesNetSource,
    private val breweriesDbSource: BreweriesDbSource,
    private val netRepoMapper: BreweryNetRepoMapper,
    private val dbRepoMapper: BreweryDbRepoMapper,
    private val netDbMapper: BreweryNetDbMapper
) : BreweriesRepository {

    override suspend fun getBrewery(id: String): BreweryRepoModel =
        breweriesNetSource.getBrewery(id).let { netModel ->
            netRepoMapper.mapTo(netModel)
        }

    override suspend fun getAndSaveBreweries(page: Int, perPage: Int): List<BreweryRepoModel> {
        return breweriesNetSource.getBreweries(page, perPage).let { netList ->
            netList.map { netModel ->
                netDbMapper.mapTo(netModel)
            }
        }.also { dbList ->
//            if (page == 1) {
//                breweriesDbSource.replaceAll(dbList)
//            } else {
//                breweriesDbSource.addOrReplace(dbList)
//            }
        }.let { netList ->
            netList.map { netModel ->
                dbRepoMapper.mapTo(netModel)
            }
        }
    }

    override suspend fun refreshBreweries(page: Int, perPage: Int) {
        breweriesNetSource.getBreweries(page, perPage).let { netList ->
            netList.map { netModel ->
                netDbMapper.mapTo(netModel)
            }
        }.also { dbList ->
            if (page == 1) {
                breweriesDbSource.replaceAll(dbList)
            } else {
                breweriesDbSource.addOrReplace(dbList)
            }
        }
    }

    override fun observeBreweriesChanges(): Flow<List<BreweryRepoModel>> {
        return breweriesDbSource.observeChanges().map { dbList ->
            dbList.map { dbModel ->
                dbRepoMapper.mapTo(dbModel)
            }
        }
    }

}