package com.example.anlisisrutastiempostransporte.presentation.panel

import com.example.anlisisrutastiempostransporte.domain.Station

sealed class PanelNavigation {
    data class ShowAllStations(val stations: List<Station>) : PanelNavigation()
    object HiddenLoading : PanelNavigation()
    object ShowLoading : PanelNavigation()
}

sealed class PanelAction {
    object GetAllStations : PanelAction()
    object ValidateFirstLaunched : PanelAction()
}