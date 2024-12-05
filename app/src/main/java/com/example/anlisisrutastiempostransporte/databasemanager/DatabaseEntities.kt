package com.example.anlisisrutastiempostransporte.databasemanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "station_name") var name: String,
    @ColumnInfo(name = "station_distance") var distance: Double
)
