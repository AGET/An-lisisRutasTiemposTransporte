package com.example.anlisisrutastiempostransporte.data

import com.example.anlisisrutastiempostransporte.domain.Station
import com.example.anlisisrutastiempostransporte.preferencesmanager.PreferencesDataSources.Companion.FIRST_LAUNCHED

class StationRepository(
    private val localStationDataSource: LocalStationsDataSource,
    private val preferenceDataSource: LocalPreferencesDataSource
) {
    //region Public Methods
    fun createStation(station: Station) =
        localStationDataSource.createStation(station)

    fun getAllStations(): List<Station> =
        localStationDataSource.getAllStations()

    fun getStationById(stationID: Int): Station =
        localStationDataSource.getStationById(stationID)

    fun updateStation(station: Station): Int =
        localStationDataSource.updateStation(station)

    fun deleteStation(station: Station) {
        localStationDataSource.deleteStation(station)
    }

    fun setStartLaunchedValue() {
        preferenceDataSource.setBooleanValue(FIRST_LAUNCHED, true)
    }

    fun getAlreadyStartedValue(): Boolean {
        return preferenceDataSource.getBooleanValue(FIRST_LAUNCHED)
    }
    //endregion
}
