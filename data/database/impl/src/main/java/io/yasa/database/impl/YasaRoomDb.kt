package io.yasa.database.impl

import androidx.room.Database
import androidx.room.RoomDatabase
import io.yasa.database.impl.dao.BreweriesDao
import io.yasa.database.model.BreweryDbModel

@Database(
    version = 1,
    entities = [
        BreweryDbModel::class
    ],
    exportSchema = false
)
abstract class YasaRoomDb : RoomDatabase() {

    abstract fun breweriesDao(): BreweriesDao

}