package com.example.anlisisrutastiempostransporte.di

import com.example.anlisisrutastiempostransporte.data.StationRepository
import com.example.anlisisrutastiempostransporte.usecases.CreateStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.DeleteStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.FirstLaunchUseCase
import com.example.anlisisrutastiempostransporte.usecases.GetAllStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.UpdateStationUseCase
import com.example.anlisisrutastiempostransporte.usecases.ValidateAlreadyStartedUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun createStationsUseCaseProvider(stationsRepository: StationRepository): CreateStationsUseCase {
        return CreateStationsUseCase(stationsRepository)
    }

    @Provides
    fun getAllStationsUseCaseProvider(stationsRepository: StationRepository): GetAllStationsUseCase {
        return GetAllStationsUseCase(stationsRepository)
    }

    @Provides
    fun updateStationUseCaseeProvider(stationsRepository: StationRepository): UpdateStationUseCase {
        return UpdateStationUseCase(stationsRepository)
    }

    @Provides
    fun deleteStationsUseCaseProvider(stationsRepository: StationRepository): DeleteStationsUseCase {
        return DeleteStationsUseCase(stationsRepository)
    }

    @Provides
    fun validateFirstLaunchUseCaseProvider(stationsRepository: StationRepository): ValidateAlreadyStartedUseCase {
        return ValidateAlreadyStartedUseCase(stationsRepository)
    }

    @Provides
    fun firstLaunchUseCase(stationsRepository: StationRepository): FirstLaunchUseCase {
        return FirstLaunchUseCase(stationsRepository)
    }
}
