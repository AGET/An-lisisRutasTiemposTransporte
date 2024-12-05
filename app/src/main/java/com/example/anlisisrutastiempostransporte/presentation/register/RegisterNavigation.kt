package com.example.anlisisrutastiempostransporte.presentation.register

import com.example.anlisisrutastiempostransporte.domain.Station

sealed class StationRegisterNavigation {
    data class ShowAllStations(val stations: List<Station>) : StationRegisterNavigation()
    object ShowLoading : StationRegisterNavigation()
    object HiddenLoading : StationRegisterNavigation()
    object SuccessRegister : StationRegisterNavigation()
    object ErrorRegister : StationRegisterNavigation()
}

sealed class StationRegisterAction {
    data class OnValidRegister(val stationModel: Station) : StationRegisterAction()
    object GetAllStations : StationRegisterAction()
}
