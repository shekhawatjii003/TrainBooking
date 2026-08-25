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
        name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_coach_seat_number",
                        columnNames = {"coach_id", "seat_number"}
                )
        }
)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(nullable = false,name = "coach_id")
    private Coach coach;
    @Column(nullable = false,name = "seat_number")
    private Integer seatNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "seat_type")
    private SeatType seatType;
    @Column(nullable = false)
    private Boolean active;


}
