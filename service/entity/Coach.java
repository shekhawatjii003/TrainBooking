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
        name = "coaches",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_train_coach_number",
                        columnNames = {"train_id", "coach_number"}
                )
        }
)
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "train_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_coach_train")
    )
    private Train train;

    @Column(
            name = "coach_number",
            nullable = false
    )
    private String coachNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "coach_type",
            nullable = false
    )
    private CoachType coachType;
}