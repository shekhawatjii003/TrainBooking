package com.example.trainbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "train_journey",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_train_id_journey_date",
                        columnNames = {"train_id", "journey_date"}
                )
        }
)
public class TrainJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="train_id",nullable = false)
    private Train train;
    @Column(nullable = false ,name="journey_date")
    private LocalDate journeyDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "journey_status")
    private JourneyStatus status;
    @Column(nullable = false)
    private Boolean active;
}
