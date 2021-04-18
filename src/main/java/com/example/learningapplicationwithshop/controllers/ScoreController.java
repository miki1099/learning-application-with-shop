package com.example.learningapplicationwithshop.controllers;

import com.example.learningapplicationwithshop.model.dto.ScoreDto;
import com.example.learningapplicationwithshop.services.implementation.ScoreServiceImpl;
import com.example.learningapplicationwithshop.services.implementation.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
public class ScoreController {
    private final ScoreServiceImpl scoreService;
    private final UserServiceImpl userService;

    @RequestMapping(value = "/admin/score/countAllBetween", method = RequestMethod.GET)
    public int countAllBetween(@RequestParam String fromDate, @RequestParam String toDate) {
        return scoreService.countAllBetween(fromDate, toDate);
    }

    @RequestMapping(value = "/admin/score/avgAllUsers", method = RequestMethod.GET)
    public double countAllBetween() {
        return scoreService.getAvgScoreAllUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/score/create/{score}", method = RequestMethod.POST)
    public ScoreDto createScore(@PathVariable int score, Principal principal) {
        return scoreService.create(getPrincipalUserId(principal), score);
    }

    @RequestMapping(value = "/score/userScoreByDate", method = RequestMethod.GET)
    public List<ScoreDto> getUserScoreSortedByDate(@RequestParam int page,@RequestParam int size,
                                                   @RequestParam int userId, @RequestParam boolean sortByAsc) {
        if(sortByAsc) return scoreService.getAllUserScoreOrderByDateAsc(page, size, userId);
        else return scoreService.getAllUserScoreOrderByDateDesc(page, size, userId);
    }

    @RequestMapping(value = "/score/userScoreByScore", method = RequestMethod.GET)
    public List<ScoreDto> getUserScoreSortedByScore(@RequestParam int page,@RequestParam int size,
                                                   @RequestParam int userId, @RequestParam boolean sortByAsc) {
        if(sortByAsc) return scoreService.getAllUserScoreOrderByScoreAsc(page, size, userId);
        else return scoreService.getAllUserScoreOrderByScoreDesc(page, size, userId);
    }

    @RequestMapping(value = "/score/getAvg/{userId}", method = RequestMethod.GET)
    public double getUserAvgScore(@PathVariable int userId) {
        return scoreService.getUserAvgScore(userId);
    }

    @RequestMapping(value = "/score/getBestScore/{userId}", method = RequestMethod.GET)
    public ScoreDto getUserBestScore(@PathVariable int userId) {
        return scoreService.getUserBestScore(userId);
    }

    @RequestMapping(value = "/score/getPageCount/{userId}", method = RequestMethod.GET)
    public int getUserScorePagesCount(@RequestParam int size, @PathVariable int userId) {
        return scoreService.getUserScorePages(size, userId);
    }

    private int getPrincipalUserId(Principal principal) {
        return userService.findByLogin(principal.getName()).getId();
    }
}
