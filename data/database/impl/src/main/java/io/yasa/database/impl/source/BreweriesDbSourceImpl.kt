package io.yasa.database.impl.source

import io.yasa.database.impl.YasaRoomDb
import io.yasa.database.impl.dao.BreweriesDao
import io.yasa.database.model.BreweryDbModel
import io.yasa.database.source.BreweriesDbSource

class BreweriesDbSourceImpl(
    private val breweriesDao: BreweriesDao,
    private val database: YasaRoomDb
) : BreweriesDbSource {

    override suspend fun getBrewery(id: String): BreweryDbModel {
        return breweriesDao.get(id)
    }

    override suspend fun getBreweries(page: Int, perPage: Int): List<BreweryDbModel> {
        return breweriesDao.get()
    }

}