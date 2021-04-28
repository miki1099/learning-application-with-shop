package com.example.learningapplicationwithshop.services.implementation;

import com.example.learningapplicationwithshop.exceptions.UserNotFoundException;
import com.example.learningapplicationwithshop.model.Score;
import com.example.learningapplicationwithshop.model.User;
import com.example.learningapplicationwithshop.model.dto.ScoreDto;
import com.example.learningapplicationwithshop.repositories.ScoreRepository;
import com.example.learningapplicationwithshop.repositories.UserRepository;
import com.example.learningapplicationwithshop.services.ScoreService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public ScoreDto create(int userId, int score, String category) {
        User user = findUserSafe(userId);
        Score scoreToSave = new Score();
        scoreToSave.setScore(score);
        scoreToSave.setUserId(user);
        scoreToSave.setScoreDate(LocalDate.now());
        scoreToSave.setCategory(category);
        return modelMapper.map(scoreRepository.save(scoreToSave), ScoreDto.class);
    }

    @Override
    public List<ScoreDto> getAllUserScoreOrderByDateAsc(int page, int size, int userId) {
        User user = findUserSafe(userId);
        Pageable pageable = PageRequest.of(page, size);
        return scoreRepository.findAllByUserIdOrderByScoreDateAsc(pageable, user)
                .stream().map(score -> modelMapper.map(score, ScoreDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScoreDto> getAllUserScoreOrderByDateDesc(int page, int size, int userId) {
        User user = findUserSafe(userId);
        Pageable pageable = PageRequest.of(page, size);
        return scoreRepository.findAllByUserIdOrderByScoreDateDesc(pageable, user)
                .stream().map(score -> modelMapper.map(score, ScoreDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScoreDto> getAllUserScoreOrderByScoreAsc(int page, int size, int userId) {
        User user = findUserSafe(userId);
        Pageable pageable = PageRequest.of(page, size);
        return scoreRepository.findAllByUserIdOrderByScoreAsc(pageable, user)
                .stream().map(score -> modelMapper.map(score, ScoreDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ScoreDto> getAllUserScoreOrderByScoreDesc(int page, int size, int userId) {
        User user = findUserSafe(userId);
        Pageable pageable = PageRequest.of(page, size);
        return scoreRepository.findAllByUserIdOrderByScoreDesc(pageable, user)
                .stream().map(score -> modelMapper.map(score, ScoreDto.class)).collect(Collectors.toList());
    }

    @Override
    public int countAllBetween(String fromDate, String toDate) {
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to  = LocalDate.parse(toDate);
        return scoreRepository.countAllByScoreDateBetween(from, to);
    }

    @Override
    public double getAvgScoreAllUsers() {
        return scoreRepository.getAvgScoreAllUsers();
    }

    @Override
    public double getUserAvgScore(int userId) {
        User user = findUserSafe(userId);
        return scoreRepository.getAvgUserScore(user.getId());
    }

    @Override
    public ScoreDto getUserBestScore(int userId) {
        User user = findUserSafe(userId);
        return modelMapper.map(scoreRepository.findBiggestUserScore(user.getId()), ScoreDto.class);
    }

    @Override
    public int getUserScorePages(int size, int userId) {
        User user = findUserSafe(userId);
        Pageable pageable = PageRequest.of(0, size);
        return scoreRepository.findAllByUserIdOrderByScoreDesc(pageable, user).getTotalPages();
    }

    private User findUserSafe(int userId) {
        if(userRepository.existsById(userId)) {
            return userRepository.getOne(userId);
        } else {
            throw new UserNotFoundException("id: " + userId);
        }
    }

}
