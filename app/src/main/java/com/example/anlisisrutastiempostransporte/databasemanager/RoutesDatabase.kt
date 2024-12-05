package com.example.anlisisrutastiempostransporte.databasemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [StationEntity::class], version = 1)
@TypeConverters(ListStringConverters::class)
abstract class RoutesDatabase : RoomDatabase() {

    //region Abstract Methods
    abstract fun stationDao(): StationDao
    //endregion

    //region Companion Object
    companion object {

        private const val DATABASE_NAME = "routes_db"

        @Synchronized
        fun getDatabase(context: Context): RoutesDatabase = Room.databaseBuilder(
            context.applicationContext,
            RoutesDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    //endregion
}
