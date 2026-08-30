package com.example.trainbooking.service;

import com.example.trainbooking.entity.Booking;
import com.example.trainbooking.entity.Passenger;
import com.example.trainbooking.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import com.example.trainbooking.exception.ResourceNotFoundException;
import com.example.trainbooking.exception.DuplicateResourceException;
import com.example.trainbooking.exception.InvalidRequestException;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(
            PassengerRepository passengerRepository) {

        this.passengerRepository = passengerRepository;
    }


    @Override
    public Passenger createPassenger(Passenger passenger) {

        if (passengerRepository
                .existsByBookingAndNameAndAgeAndGender(
                        passenger.getBooking(),
                        passenger.getName(),
                        passenger.getAge(),
                        passenger.getGender())) {

            throw new DuplicateResourceException(
                    "Passenger already exists in this booking"
            );
        }

        // Important because active is NOT NULL
        if (passenger.getActive() == null) {
            passenger.setActive(true);
        }

        return passengerRepository.save(passenger);
    }


    @Override
    public Passenger getPassenger(Long id) {

        Optional<Passenger> passenger =
                passengerRepository.findById(id);

        if (passenger.isPresent()) {
            return passenger.get();
        }

        throw new ResourceNotFoundException(
                "Passenger not found"
        );
    }


    @Override
    public List<Passenger> getPassengers() {

        List<Passenger> passengers =
                passengerRepository.findAll();

        if (passengers.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Passenger not found"
            );
        }

        return passengers;
    }


    @Override
    public Passenger updatePassenger(
            Long id,
            Passenger passenger) {

        Optional<Passenger> passengerOptional =
                passengerRepository.findById(id);

        if (!passengerOptional.isPresent()) {
            throw new ResourceNotFoundException(
                    "Passenger not found"
            );
        }

        Passenger oldPassenger =
                passengerOptional.get();

        // Booking cannot be changed
        if (!oldPassenger.getBooking()
                .equals(passenger.getBooking())) {

            throw new InvalidRequestException(
                    "Booking cannot be updated"
            );
        }

        oldPassenger.setAge(
                passenger.getAge()
        );

        oldPassenger.setName(
                passenger.getName()
        );

        oldPassenger.setGender(
                passenger.getGender()
        );

        return passengerRepository.save(
                oldPassenger
        );
    }


    @Override
    public Passenger deletePassenger(Long id) {

        Optional<Passenger> passengerOptional =
                passengerRepository.findById(id);

        if (!passengerOptional.isPresent()) {
            throw new ResourceNotFoundException(
                    "Passenger not found"
            );
        }

        Passenger oldPassenger =
                passengerOptional.get();

        oldPassenger.setActive(false);

        return passengerRepository.save(
                oldPassenger
        );
    }


    @Override
    public List<Passenger> getPassengerByBooking(
            Booking booking) {

        List<Passenger> passengers =
                passengerRepository.findByBooking(
                        booking
                );

        if (passengers.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Passenger not found"
            );
        }

        return passengers;
    }
}