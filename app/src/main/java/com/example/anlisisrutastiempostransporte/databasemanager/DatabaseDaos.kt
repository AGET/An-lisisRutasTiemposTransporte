package com.example.anlisisrutastiempostransporte.databasemanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStation(stationEntity: StationEntity)

    @Query("SELECT * FROM Stations")
    fun getAllStations(): List<StationEntity>

    @Query("SELECT * FROM Stations WHERE uid = :id")
    fun getStationById(id: Int): StationEntity

    @Query("SELECT * FROM Stations WHERE uid IN (:stationsIds)")
    fun loadAllByIds(stationsIds: IntArray): List<StationEntity>

    @Query("SELECT * FROM Stations WHERE station_name LIKE :name LIMIT 1")
    fun findByName(name: String): StationEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStation(stationEntity: StationEntity): Int

    @Delete
    fun deleteStation(stationEntity: StationEntity): Int
}
