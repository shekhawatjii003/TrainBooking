package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Passenger createPassenger(Passenger passenger) {

        if (passengerRepository.existsByBookingAndNameAndAgeAndGender(
                passenger.getBooking(),
                passenger.getName(),
                passenger.getAge(),
                passenger.getGender())) {

            throw new RuntimeException("Passenger already exists in this booking");
        }

        return passengerRepository.save(passenger);
    }

    @Override
    public Passenger getPassenger(Long id) {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if(passenger.isPresent()){
            return passenger.get();
        }
        throw new RuntimeException("Passenger not found");
    }

    @Override
    public List<Passenger> getPassengers() {
        List<Passenger> passengers = passengerRepository.findAll();
        if(passengers.isEmpty()){
            throw new RuntimeException("Passenger not found");
        }
        return passengers;
    }

    @Override
    public Passenger updatePassenger(Long id, Passenger passenger) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if(!passengerOptional.isPresent()){
            throw new RuntimeException("Passenger not found");
        }
        Passenger oldPassenger = passengerOptional.get();
        if(!oldPassenger.getBooking().equals(passenger.getBooking())){
            throw new RuntimeException("Booking can not update");
        }
        oldPassenger.setAge(passenger.getAge());
        oldPassenger.setName(passenger.getName());
        oldPassenger.setGender(passenger.getGender());
        return  passengerRepository.save(oldPassenger);
    }

    @Override
    public Passenger deletePassenger(Long id) {
        Optional<Passenger> passengerOptional = passengerRepository.findById(id);
        if(!passengerOptional.isPresent()){
            throw new RuntimeException("Passenger not found");
        }
        Passenger oldPassenger = passengerOptional.get();
        oldPassenger.setActive(false);
        return passengerRepository.save(oldPassenger);
    }

    @Override
    public List<Passenger> getPassengerByBooking(Booking booking) {
        List<Passenger> passengers = passengerRepository.findByBooking(booking);
        if(passengers.isEmpty()){
            throw new RuntimeException("Passenger not found");
        }
        return passengers;
    }
}
