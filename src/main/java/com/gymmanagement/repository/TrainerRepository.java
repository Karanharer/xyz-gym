package com.gymmanagement.repository;

import java.util.List;

import com.gymmanagement.model.Trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
    Trainer findByEmailAndPassword(String email, String password);

    public List<Trainer> findTop3ByOrderByIdAsc();
}
