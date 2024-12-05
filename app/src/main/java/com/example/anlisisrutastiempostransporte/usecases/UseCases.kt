package com.example.anlisisrutastiempostransporte.usecases

import com.example.anlisisrutastiempostransporte.data.StationRepository
import com.example.anlisisrutastiempostransporte.domain.Station

class ValidateAlreadyStartedUseCase(private val stationRepository: StationRepository) {
    fun invoke(): Boolean =
        stationRepository.getAlreadyStartedValue()
}

class FirstLaunchUseCase(private val stationRepository: StationRepository) {
    fun invoke() =
        stationRepository.setStartLaunchedValue()
}

class CreateStationsUseCase(private val stationRepository: StationRepository) {
    fun invoke(station: Station) =
        stationRepository.createStation(station)
}

class GetAllStationsUseCase(private val stationRepository: StationRepository) {
    fun invoke(): List<Station> =
        stationRepository.getAllStations()
}

class UpdateStationUseCase(private val stationRepository: StationRepository) {
    fun invoke(station: Station): Int =
        stationRepository.updateStation(station)
}

class DeleteStationsUseCase(private val stationRepository: StationRepository) {
    fun invoke(station: Station): Int =
        stationRepository.updateStation(station)
}
