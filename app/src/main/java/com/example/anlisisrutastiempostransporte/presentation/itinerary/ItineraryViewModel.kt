package com.example.anlisisrutastiempostransporte.presentation.itinerary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anlisisrutastiempostransporte.presentation.ViewModelContract
import com.example.anlisisrutastiempostransporte.presentation.events.Event
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class ItineraryViewModel : ViewModel(), ViewModelContract<ItineraryAction> {

    private val _events = MutableLiveData<Event<ItineraryNavigation>>()
    val events: LiveData<Event<ItineraryNavigation>> get() = _events
    private lateinit var itineraryModel: ItineraryModel

    override fun onEvent(event: Event<ItineraryAction>?) {
        viewModelScope.launch {
            event?.getContentIfNotHandled()?.let { navigation ->
                if (navigation is ItineraryAction.OnCalculate) {
                    navigation.run {
                        this@ItineraryViewModel.itineraryModel = itineraryModel
                        calculateTripTime()
                    }
                }
            }
        }
    }

    private fun calculateTripTime() {
        var stations = itineraryModel.allStations
        val startTrip = itineraryModel.startStation
        val endTrip = itineraryModel.endStation
        val speed = itineraryModel.speed
        val timeWaiting = itineraryModel.averageWaitingTime

        //Los datos de la lista "stations" son obtenidos de una base de datos
        // Con una lista ya ordenada por distancias podemos obtener la suma de los
        // tiempos de espera por cada estación
        val stationsOrdened = stations.sortedBy { it.startTerminalLength }
        val starIndex = stationsOrdened.indexOf(startTrip)
        val endIndex = stationsOrdened.indexOf(endTrip)
        // Se calcula la distancia encontrando la posicion de la estacion de inicio - la distancia de la posicion final
        // Se considera el valor absoluto por que puede ir de sur a norte o de norte a sur
        val distance = (startTrip.startTerminalLength - endTrip.startTerminalLength).absoluteValue
        //Se calculan los minutos recorridos dividiendo la distancia entre la velocidad del tren
        val timeTrip = distance / speed * 60
        // Obtenemos el numero de estaciones a recorrer para calcular el costo y el tiempo
        val numberStations = (starIndex - endIndex).absoluteValue
        //Tiempo total, el la suma del tiempo de abordaje y el tiempo de viaje
        val timeWaitingTotal = (numberStations * timeWaiting)
        // Tiempo total del recorrido
        val timeTotal = timeTrip + timeWaitingTotal
        //Costo, este es por KM recorrido y se calcula multiplicando por la distancia
        val cost = distance * itineraryModel.price

        val model = ItineraryResultModel(
            startTrip,
            endTrip,
            distance,
            cost,
            numberStations,
            timeWaiting,
            tripTime = timeTrip,
            tripTimeTotal = timeTotal,
            speed = speed
        )
        _events.value = Event(ItineraryNavigation.Itinerary(model))
    }
}
