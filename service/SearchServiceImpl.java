package com.example.trainbooking.service;

import com.example.trainbooking.entity.Station;
import com.example.trainbooking.entity.Train;
import com.example.trainbooking.entity.TrainJourney;
import com.example.trainbooking.entity.TrainStop;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final StationService stationService;
    private final TrainStopService trainStopService;
    private final TrainJourneyService trainJourneyService;

    public SearchServiceImpl(
            StationService stationService,
            TrainStopService trainStopService,
            TrainJourneyService trainJourneyService) {

        this.stationService = stationService;
        this.trainStopService = trainStopService;
        this.trainJourneyService = trainJourneyService;
    }

    @Override
    public List<TrainJourney> searchTrains(
            String fromStationCode,
            String toStationCode,
            LocalDate journeyDate) {

        Station fromStation =
                stationService.getStationByCode(fromStationCode);

        Station toStation =
                stationService.getStationByCode(toStationCode);

        List<TrainStop> fromStops =
                trainStopService.getTrainStopsByStation(fromStation);

        List<TrainStop> toStops =
                trainStopService.getTrainStopsByStation(toStation);

        List<TrainJourney> result = new ArrayList<>();

        for (TrainStop fromStop : fromStops) {

            Train train = fromStop.getTrain();

            for (TrainStop toStop : toStops) {

                if (toStop.getTrain().getId().equals(train.getId())
                        && fromStop.getStopSequence()
                        < toStop.getStopSequence()) {

                    try {
                        TrainJourney journey =
                                trainJourneyService
                                        .getJourneyByTrainAndDate(
                                                train,
                                                journeyDate
                                        );

                        if (journey.getActive()) {
                            result.add(journey);
                        }

                    } catch (RuntimeException e) {
                        throw new RuntimeException("No Journey is existing for particular date");
                    }

                    break;
                }
            }
        }

        return result;
    }
}