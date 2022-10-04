package io.yasa.database.impl.di

import android.content.Context
import androidx.room.Room
import io.yasa.database.impl.YasaRoomDb
import io.yasa.database.impl.dao.BreweriesDao
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal val roomModule = Kodein.Module("roomModule") {
    bind<YasaRoomDb>() with singleton { createRoomDatabase(instance()) }
    bind<BreweriesDao>() with singleton { instance<YasaRoomDb>().breweriesDao() }
}

private fun createRoomDatabase(context: Context): YasaRoomDb {
    return Room.databaseBuilder(
        context.applicationContext,
        YasaRoomDb::class.java,
        YasaRoomDb::class.java.simpleName
    )
        .fallbackToDestructiveMigration()
        .build()
}