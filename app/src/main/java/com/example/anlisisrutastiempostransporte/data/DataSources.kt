package com.example.anlisisrutastiempostransporte.data

import com.example.anlisisrutastiempostransporte.domain.Station

interface LocalStationsDataSource {
    fun createStation(station: Station)
    fun getAllStations(): List<Station>
    fun getStationById(id: Int): Station
    fun updateStation(station: Station): Int
    fun deleteStation(station: Station): Int
}
