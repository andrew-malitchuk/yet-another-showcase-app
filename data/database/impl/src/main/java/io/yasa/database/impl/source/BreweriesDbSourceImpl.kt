package io.yasa.database.impl.source

import io.yasa.database.impl.dao.BreweriesDao
import io.yasa.database.model.BreweryDbModel
import io.yasa.database.source.BreweriesDbSource
import kotlinx.coroutines.flow.Flow

class BreweriesDbSourceImpl(
    private val breweriesDao: BreweriesDao,
) : BreweriesDbSource {

    override suspend fun getBrewery(id: String): BreweryDbModel {
        return breweriesDao.get(id)
    }

    override suspend fun getBreweries(page: Int, perPage: Int): List<BreweryDbModel> {
        return breweriesDao.getAll()
    }

    override suspend fun replaceAll(list: List<BreweryDbModel>) {
        breweriesDao.replaceAllBreweries(list)
    }

    override suspend fun addOrReplace(list: List<BreweryDbModel>) {
        breweriesDao.insertAll(list)
    }

    override fun observeChanges(): Flow<List<BreweryDbModel>> {
        return breweriesDao.observeChanges()
    }

}