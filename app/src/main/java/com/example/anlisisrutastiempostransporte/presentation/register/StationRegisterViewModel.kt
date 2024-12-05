package com.example.anlisisrutastiempostransporte.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.presentation.ViewModelContract
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterNavigation.HiddenLoading
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterNavigation.ShowAllStations
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterNavigation.ShowLoading
import com.example.anlisisrutastiempostransporte.presentation.register.StationRegisterNavigation.SuccessRegister
import com.example.anlisisrutastiempostransporte.usecases.CreateStationsUseCase
import com.example.anlisisrutastiempostransporte.usecases.GetAllStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StationRegisterViewModel(
    private val createStationsUseCase: CreateStationsUseCase,
    private val getStationUseCase: GetAllStationsUseCase
) : ViewModel(), ViewModelContract<StationRegisterAction> {

    private val _events = MutableLiveData<Event<StationRegisterNavigation>>()
    val events: LiveData<Event<StationRegisterNavigation>> get() = _events

    override fun onEvent(event: Event<StationRegisterAction>?) {
        viewModelScope.launch {
            event?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    is StationRegisterAction.OnValidRegister -> navigation.run {
                        doComputation(
                            stationModel
                        )
                    }

                    StationRegisterAction.GetAllStations -> {
                        getAllStations()
                    }
                }
            }
        }
    }

    private suspend fun doComputation(station: Station) {
        _events.value = Event(ShowLoading)
        withContext(Dispatchers.IO) {
            createStationsUseCase.invoke(station)
        }
        _events.value = Event(HiddenLoading)
        _events.value = Event(SuccessRegister)
    }

    private suspend fun getAllStations() {
        _events.value = Event(ShowLoading)
        val stations: List<Station>
        withContext(Dispatchers.IO) {
            stations = getStationUseCase.invoke()
        }
        _events.value = Event(ShowAllStations(stations))
        _events.value = Event(HiddenLoading)
    }
}
