package com.example.anlisisrutastiempostransporte.presentation.panel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.presentation.ViewModelContract
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelNavigation.HiddenLoading
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelNavigation.ShowAllStations
import com.example.anlisisrutastiempostransporte.presentation.panel.PanelNavigation.ShowLoading
import com.example.anlisisrutastiempostransporte.usecases.CreateStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.FirstLaunchUseCase
import com.example.anlisisrutastiempostransporte.usecases.GetAllStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.ValidateAlreadyStartedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PanelViewModel(
    private val getStationUseCase: GetAllStationsUseCase,
    private val createStationUseCase: CreateStationsUseCase,
    private val validateAlreadyStartedUseCase: ValidateAlreadyStartedUseCase,
    private val firstLaunchUseCase: FirstLaunchUseCase
) : ViewModel(), ViewModelContract<PanelAction> {

    private val _events = MutableLiveData<Event<PanelNavigation>>()
    val events: LiveData<Event<PanelNavigation>> get() = _events


    override fun onEvent(event: Event<PanelAction>?) {
        viewModelScope.launch {
            event?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    PanelAction.GetAllStations -> getAllStations()
                    PanelAction.ValidateFirstLaunched -> validateFirstLaunched()
                }
            }
        }
    }

    private suspend fun getAllStations() {
        _events.value = Event(ShowLoading)
        val stations: List<Station>
        withContext(Dispatchers.IO) {
            stations = getStationUseCase.invoke()
        }
        if (stations.isNotEmpty()) {
            _events.value = Event(ShowAllStations(stations.sortedBy { it.startTerminalLength }))
        }
        _events.value = Event(HiddenLoading)
    }

    private suspend fun validateFirstLaunched() {

        _events.value = Event(ShowLoading)
        var started: Boolean
        withContext(Dispatchers.IO) {
            started = validateAlreadyStartedUseCase.invoke()
            if (started.not()) {
                setDefaultData()
                firstLaunchUseCase.invoke()
            }
        }

        if (started.not()) {
            getAllStations()
        }
        _events.value = Event(HiddenLoading)
    }

    private fun setDefaultData() {
        createStationUseCase.invoke(Station(name = "Taxqueña", startTerminalLength = 0.0))
        createStationUseCase.invoke(Station(name = "General Anaya", startTerminalLength = 1.33))
        createStationUseCase.invoke(Station(name = "Ermita", startTerminalLength = 2.17))
        createStationUseCase.invoke(Station(name = "Portales", startTerminalLength = 2.9))
        createStationUseCase.invoke(Station(name = "Nativitas", startTerminalLength = 3.840))
        createStationUseCase.invoke(Station(name = "Villa de Cortés", startTerminalLength = 4.590))
        createStationUseCase.invoke(Station(name = "Xola", startTerminalLength = 5.28))
        createStationUseCase.invoke(Station(name = "Viaducto", startTerminalLength = 5.778))
        createStationUseCase.invoke(Station(name = "Chabacano", startTerminalLength = 6.552))
        createStationUseCase.invoke(Station(name = "San Antonio Abad ", startTerminalLength = 7.194))
        createStationUseCase.invoke(Station(name = "Pino Suárez ", startTerminalLength = 8.011))
        createStationUseCase.invoke(Station(name = "Zócalo ", startTerminalLength = 8.756))
        createStationUseCase.invoke(Station(name = "Allende ", startTerminalLength = 9.358))
        createStationUseCase.invoke(Station(name = "Bellas Artes  ", startTerminalLength = 9.745))
        createStationUseCase.invoke(Station(name = "Hidalgo  ", startTerminalLength = 10.192))
        createStationUseCase.invoke(Station(name = "Revolución  ", startTerminalLength = 10.779))
        createStationUseCase.invoke(Station(name = "San Cosme  ", startTerminalLength = 11.316))
        createStationUseCase.invoke(Station(name = "Normal   ", startTerminalLength = 11.973))
        createStationUseCase.invoke(Station(name = "Colegio Militar  ", startTerminalLength = 12.489))
        createStationUseCase.invoke(Station(name = "Popotla   ", startTerminalLength = 12.951))
        createStationUseCase.invoke(Station(name = "Cuitláhuac  ", startTerminalLength = 13.571))
        createStationUseCase.invoke(Station(name = "Tacuba  ", startTerminalLength = 14.208))
        createStationUseCase.invoke(Station(name = "Panteones  ", startTerminalLength = 15.624))
        createStationUseCase.invoke(Station(name = "Cuatro Caminos  ", startTerminalLength = 17.2))
    }
}
