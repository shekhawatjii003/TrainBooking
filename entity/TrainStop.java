package com.example.trainbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "train_stops",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_train_stop_sequence",
                        columnNames = {"train_id", "stop_sequence"}
                )
        }
)
public class TrainStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "train_id",
            nullable = false
    )
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "station_id",
            nullable = false
    )
    private Station station;

    @Column(
            name = "stop_sequence",
            nullable = false
    )
    private Long stopSequence;

    private LocalTime arrivalTime;

    private LocalTime departureTime;

    @Column(
            name = "distance_from_source",
            nullable = false
    )
    private Integer distanceFromSource;
    @Column(nullable = false)
    private boolean active;
}