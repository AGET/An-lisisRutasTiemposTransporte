package com.example.anlisisrutastiempostransporte.presentation.liststations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.presentation.ViewModelContract
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsNavigation.HiddenLoading
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsNavigation.ShowAllStations
import com.example.anlisisrutastiempostransporte.presentation.liststations.ListStationsNavigation.ShowLoading
import com.example.anlisisrutastiempostransporte.usecases.GetAllStationsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListStationsViewModel(
    private val getStationUseCase: GetAllStationsUseCase,
) : ViewModel(), ViewModelContract<ListStationsAction> {

    private val _events = MutableLiveData<Event<ListStationsNavigation>>()
    val events: LiveData<Event<ListStationsNavigation>> get() = _events


    override fun onEvent(event: Event<ListStationsAction>?) {
        viewModelScope.launch {
            event?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    ListStationsAction.GetAllStations -> getAllStations()
                }
            }
        }
    }

    private suspend fun getAllStations() {
        _events.value = Event(ShowLoading)
        val stations: List<Station>
        withContext(Dispatchers.IO) {
            stations = getStationUseCase.invoke().sortedBy { it.startTerminalLength }
        }
        _events.value = Event(ShowAllStations(stations))
        _events.value = Event(HiddenLoading)
    }
}
