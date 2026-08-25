package com.example.trainbooking.service;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class StationServiceImpl implements StationService {
    private StationRepository stationRepository;
    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public Station createStation(Station station) {
        Optional<Station>optional=stationRepository.findByStationCode(station.getStationCode());
        if(optional.isPresent()){
            throw new RuntimeException("Already exists");
        }

        return stationRepository.save(station);
    }

    @Override
    public Station getStationById(long id) {
        Optional<Station>optional=stationRepository.findById(id);
        if(!optional.isPresent()){
            throw new RuntimeException("Not exists");
        }
        return optional.get();
    }

    @Override
    public List<Station> getAllStations() {
       return stationRepository.findAll();
    }


    @Override
    public Station updateStation(Long id, Station station) {

        Optional<Station> optional = stationRepository.findById(id);

        if (!optional.isPresent()) {
            throw new RuntimeException("Station not exists");
        }

        Station existingStation = optional.get();

        // Station code should not be changed
        if (!existingStation.getStationCode().equals(station.getStationCode())) {
            throw new RuntimeException("Station code cannot be updated");
        }

        existingStation.setStationName(station.getStationName());
        existingStation.setCity(station.getCity());
        existingStation.setState(station.getState());
        existingStation.setActive(station.getActive());

        return stationRepository.save(existingStation);
    }

    @Override
    public Station deleteStation(Long id) {
        Optional<Station>optional=stationRepository.findById(id);
        if(!optional.isPresent()){
            throw new RuntimeException("Not exists");
        }
        optional.get().setActive(false);
        stationRepository.save(optional.get());
        return optional.get();
    }

    @Override
    public Station getStationByCode(String code) {
       Optional<Station>optional=stationRepository.findByStationCode(code);
       if(!optional.isPresent()){
           throw new RuntimeException("Not exists");
       }
       return optional.get();
    }

    @Override
    public List<Station> searchStation(String city, String state) {
      List<Station>curr=stationRepository.findByCityAndState(city,state);
      if(curr.isEmpty()){
          throw new RuntimeException("Not exists");
      }
      return curr;

    }

    @Override
    public List<Station> findAllStationByActiveTrue() {
        List<Station>curr=stationRepository.findAllStationByActiveTrue();
        if(curr.isEmpty()){
            throw new RuntimeException("Not exists");
        }
        return curr;
    }
}
