package com.gymmanagement.service;

import com.gymmanagement.model.Trainer;
import java.util.List;

public interface TrainerService {

    void saveTrainer(Trainer trainer);

    List<Trainer> getAllTrainers();

    Trainer login(String email, String password);

    long getTotalTrainers();
    List<Trainer> getTopTrainers();
}
