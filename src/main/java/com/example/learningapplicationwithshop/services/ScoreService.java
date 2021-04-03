package com.example.learningapplicationwithshop.services;

import com.example.learningapplicationwithshop.model.dto.ScoreDto;
import com.example.learningapplicationwithshop.model.dto.ScoreSaveDto;

import java.util.List;

public interface ScoreService {

    ScoreDto create(ScoreSaveDto scoreSaveDto);

    List<ScoreDto> getAllUserScoreOrderByDateAsc(int page, int size, int userId);

    List<ScoreDto> getAllUserScoreOrderByDateDesc(int page, int size, int userId);

    List<ScoreDto> getAllUserScoreOrderByScoreAsc(int page, int size, int userId);

    List<ScoreDto> getAllUserScoreOrderByScoreDesc(int page, int size, int userId);

    int countAllBetween(String fromDate, String toDate);

    double getAvgScoreAllUsers();

    double getUserAvgScore(int userId);

    ScoreDto getUserBestScore(int userId);

    int getUserScorePages(int size, int userId);
}