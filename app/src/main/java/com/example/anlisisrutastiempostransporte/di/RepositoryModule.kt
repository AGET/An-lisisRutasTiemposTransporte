package com.example.anlisisrutastiempostransporte.di

import com.example.anlisisrutastiempostransporte.data.LocalPreferencesDataSource
import com.example.anlisisrutastiempostransporte.data.LocalStationsDataSource
import com.example.anlisisrutastiempostransporte.data.StationRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun stationsRepositoryProvider(
        localStationsDataSource: LocalStationsDataSource,
        preferencesDataSources: LocalPreferencesDataSource
    ): StationRepository {
        return StationRepository(localStationsDataSource, preferencesDataSources)
    }
}
