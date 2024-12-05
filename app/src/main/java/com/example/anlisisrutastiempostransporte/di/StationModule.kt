package com.example.anlisisrutastiempostransporte.di

import com.example.anlisisrutastiempostransporte.presentation.itinerary.ItineraryViewModel
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsViewModel
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelViewModel
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterViewModel
import com.example.anlisisrutastiempostransporte.usecases.CreateStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.FirstLaunchUseCase
import com.example.anlisisrutastiempostransporte.usecases.GetAllStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.ValidateAlreadyStartedUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class StationModule {
    @Provides
    fun stationViewModelProvider(
        createStationsUseCase: CreateStationsUseCase,
        getAllStationsUseCase: GetAllStationsUseCase
    ): StationRegisterViewModel {
        return StationRegisterViewModel(
            createStationsUseCase,
            getAllStationsUseCase
        )
    }

    @Provides
    fun panelViewModelProvider(
        getAllStationsUseCase: GetAllStationsUseCase,
        createStationsUseCase: CreateStationsUseCase,
        validateAlreadyStartedUseCase: ValidateAlreadyStartedUseCase,
        firstLaunchUseCase: FirstLaunchUseCase
    ): PanelViewModel {
        return PanelViewModel(
            getAllStationsUseCase,
            createStationsUseCase,
            validateAlreadyStartedUseCase,
            firstLaunchUseCase
        )
    }


    @Provides
    fun listStationsViewModelProvider(
        getAllStationsUseCase: GetAllStationsUseCase
    ): ListStationsViewModel {
        return ListStationsViewModel(
            getAllStationsUseCase
        )
    }

    @Provides
    fun itineraryViewModelProvider(): ItineraryViewModel {
        return ItineraryViewModel()
    }
}

@Subcomponent(modules = [(StationModule::class)])
interface StationComponent {
    val stationRegisterViewModel: StationRegisterViewModel
    val panelViewModel: PanelViewModel
    val listStationsViewModel: ListStationsViewModel
    val itineraryViewModel: ItineraryViewModel
}