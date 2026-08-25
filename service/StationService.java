package com.example.trainbooking.service;

import com.example.trainbooking.entity.Station;

import java.util.List;

public interface StationService {
    Station createStation(Station station);
    Station getStationById(long id);
    List<Station>getAllStations();
    Station updateStation(Long id,Station station);
    Station deleteStation(Long id);
    Station getStationByCode(String code);
    List<Station> searchStation(String city,String state);
    List<Station>findAllStationByActiveTrue();
}
