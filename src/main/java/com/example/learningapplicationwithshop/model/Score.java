package com.example.learningapplicationwithshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
