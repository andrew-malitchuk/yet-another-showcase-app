package io.yasa.database.impl.dao

import androidx.room.*
import io.yasa.database.model.BreweryDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BreweriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: BreweryDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<BreweryDbModel>)

    @Query("SELECT * FROM Breweries")
    suspend fun getAll(): List<BreweryDbModel>

    @Query("SELECT * FROM Breweries WHERE id = :id")
    suspend fun get(id: String): BreweryDbModel

    @Transaction
    fun replaceAllBreweries(list: List<BreweryDbModel>) {
        deleteAll()
        insertAll(list)
    }

    @Query("DELETE FROM Breweries")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<BreweryDbModel>)

    @Query("SELECT * FROM Breweries")
    fun observeChanges(): Flow<List<BreweryDbModel>>
}