package com.example.learningapplicationwithshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "score")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "percentage_score")
    @Min(0)
    @Max(100)
    private int score;

    @Column(name = "score_date")
    private LocalDate scoreDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private String category;
}
