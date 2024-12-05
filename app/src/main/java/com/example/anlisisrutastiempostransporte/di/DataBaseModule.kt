package com.example.anlisisrutastiempostransporte.di

import android.app.Application
import android.content.SharedPreferences
import com.example.anlisisrutastiempostransporte.data.LocalPreferencesDataSource
import com.example.anlisisrutastiempostransporte.data.LocalStationsDataSource
import com.example.anlisisrutastiempostransporte.databasemanager.RoutesDatabase
import com.example.anlisisrutastiempostransporte.databasemanager.StationDaoRoomDataSource
import com.example.anlisisrutastiempostransporte.preferencesmanager.PreferencesDataSources
import com.example.anlisisrutastiempostransporte.preferencesmanager.RoutesPreferences
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @Provides
    fun databaseProvider(app: Application): RoutesDatabase {
        return RoutesDatabase.getDatabase(app)
    }

    @Provides
    fun localStationsDataSourceProvider(
        database: RoutesDatabase
    ): LocalStationsDataSource {
        return StationDaoRoomDataSource(database)
    }

    @Provides
    fun preferencesProvider(app: Application): SharedPreferences {
        return RoutesPreferences.getPreferences(app)
    }

    @Provides
    fun preferencesDataSourceProvider(
        preferences: SharedPreferences
    ): LocalPreferencesDataSource {
        return PreferencesDataSources(preferences)
    }
}
