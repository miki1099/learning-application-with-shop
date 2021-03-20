package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
}
