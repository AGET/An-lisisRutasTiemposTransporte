package com.example.anlisisrutastiempostransporte.databasemanager

import com.example.anlisisrutastiempostransporte.domain.Station

fun List<StationEntity>.toStationsDomainList() = map(StationEntity::toStationDomain)

fun StationEntity.toStationDomain() = Station(
    uid,
    name,
    distance
)

fun List<Station>.toStationsEntityList() = map(Station::toStationEntity)

fun Station.toStationEntity() = StationEntity(
    id,
    name,
    startTerminalLength
)
