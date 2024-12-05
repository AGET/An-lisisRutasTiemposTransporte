package com.example.anlisisrutastiempostransporte.databasemanager

import com.example.anlisisrutastiempostransporte.data.LocalStationsDataSource
import com.example.anlisisrutastiempostransporte.domain.Station

class StationDaoRoomDataSource(
    database: RoutesDatabase
) : LocalStationsDataSource {

    //region Fields
    private val stationDao by lazy { database.stationDao() }
    //endregion

    //region methods
    override fun createStation(station: Station) =
        stationDao.insertStation(station.toStationEntity())

    override fun getAllStations(): List<Station> =
        stationDao.getAllStations().toStationsDomainList()

    override fun getStationById(id: Int): Station {
        return stationDao.getStationById(id).toStationDomain()
    }

    override fun updateStation(station: Station): Int {
        val stationEntity = station.toStationEntity()
        return stationDao.updateStation(stationEntity)
    }

    override fun deleteStation(station: Station): Int {
        val stationEntity = station.toStationEntity()
        return stationDao.deleteStation(stationEntity)
    }
    //end methods
}
