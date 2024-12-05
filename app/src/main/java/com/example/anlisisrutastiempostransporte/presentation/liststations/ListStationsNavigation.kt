package com.example.anlisisrutastiempostransporte.presentation.liststations

import com.example.anlisisrutastiempostransporte.domain.Station

sealed class ListStationsNavigation {
    data class ShowAllStations(val stations: List<Station>) : ListStationsNavigation()
    object HiddenLoading : ListStationsNavigation()
    object ShowLoading : ListStationsNavigation()
}

sealed class ListStationsAction {
    object GetAllStations : ListStationsAction()
}