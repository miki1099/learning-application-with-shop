package com.example.learningapplicationwithshop.repositories;

import com.example.learningapplicationwithshop.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    long count();

    Page<Question> findAll(Pageable pageable);

    @Query(nativeQuery=true, value="SELECT *  FROM question ORDER BY rand() LIMIT :amount")
    List<Question> getSpecificAmount(@Param("amount") int amount);

    Page<Question> findAllByCategory(String category, Pageable pageable);

    long countByCategory(String category);

}
