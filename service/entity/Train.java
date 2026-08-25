package com.example.trainbooking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, unique = true)
    private Long trainNumber;
    @Column(nullable = false,unique = true)
    private String trainName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private type trainType;
    @Column(nullable = false)
    private boolean active;
}
