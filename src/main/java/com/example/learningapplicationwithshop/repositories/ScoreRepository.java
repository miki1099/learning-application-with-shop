package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Score;
import com.example.learningapplicationwithshop.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ScoreRepository extends JpaRepository<Score, Integer> {

    Page<Score> findAllByUserIdOrderByScoreDateAsc(Pageable pageable, User user);

    Page<Score> findAllByUserIdOrderByScoreDateDesc(Pageable pageable, User user);

    Page<Score> findAllByUserIdOrderByScoreAsc(Pageable pageable, User user);

    Page<Score> findAllByUserIdOrderByScoreDesc(Pageable pageable, User user);

    int countAllByScoreDateBetween(LocalDate from, LocalDate to);

    @Query(nativeQuery=true, value = "SELECT avg(percentage_score) FROM score")
    double getAvgScoreAllUsers();

    @Query(nativeQuery = true, value = "SELECT avg(percentage_score) FROM score WHERE user_id = :userId")
    double getAvgUserScore(int userId);

    @Query(nativeQuery = true, value = "SELECT * FROM score where user_id = :userId " +
            "ORDER BY percentage_score DESC LIMIT 1;")
    Score findBiggestUserScore(int userId);
}
