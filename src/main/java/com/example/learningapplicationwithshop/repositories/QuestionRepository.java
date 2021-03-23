package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    long count();

    Page<Question> findAll(Pageable pageable);



}
