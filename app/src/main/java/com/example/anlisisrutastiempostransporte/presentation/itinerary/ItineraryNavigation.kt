package com.example.anlisisrutastiempostransporte.presentation.itinerary

sealed class ItineraryAction {
    data class OnCalculate(val itineraryModel: ItineraryModel) : ItineraryAction()
}

sealed class ItineraryNavigation {
    data class Itinerary(val results: ItineraryResultModel) : ItineraryNavigation()
    object ErrorData: ItineraryNavigation()
}
