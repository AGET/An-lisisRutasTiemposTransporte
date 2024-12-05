package com.example.anlisisrutastiempostransporte.presentation.itinerary

import android.os.Parcelable
import com.example.anlisisrutastiempostransporte.domain.Station
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItineraryModel(
    val price: Double,
    val speed: Int,
    val averageWaitingTime: Int,
    val startStation: Station,
    val endStation: Station,
    val allStations: List<Station>
) : Parcelable

@Parcelize
data class ItineraryResultModel(
    val startStation: Station,
    val endStation: Station,
    val distance: Double,
    val priceTotal: Double,
    val numberStations: Int,
    val waitingTime: Int,
    val tripTime: Double,
    val tripTimeTotal: Double,
    val speed: Int,
) : Parcelable

data class ItineraryItemModel(
    val icon: Int,
    val title: String,
    val detail: String,
)