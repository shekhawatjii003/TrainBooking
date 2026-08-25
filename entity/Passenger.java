package com.example.trainbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,name = "booking_id")
    private Booking booking;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,name = "seat_inventory_id")
    private SeatInventory seatInventory;

}
