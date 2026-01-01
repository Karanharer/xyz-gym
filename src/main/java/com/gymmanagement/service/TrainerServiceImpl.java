package com.gymmanagement.service;

import com.gymmanagement.model.Trainer;
import com.gymmanagement.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer login(String email, String password) {
        return trainerRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public long getTotalTrainers() {
        return trainerRepository.count();
    }

    @Override
    public List<Trainer> getTopTrainers() {
        return trainerRepository.findTop3ByOrderByIdAsc();
    }

}
