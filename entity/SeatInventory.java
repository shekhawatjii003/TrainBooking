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
@Table(
        name = "seat_inventory",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_seat_inventory_journey_seat",
                        columnNames = {"seat_id", "train_journey_id"}
                )
        }
)
public class SeatInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "train_journey_id",nullable = false)
    private TrainJourney trainJourney;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat seat;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus SeatStatus;
    @Column(nullable = false)
    private Boolean active;

}
