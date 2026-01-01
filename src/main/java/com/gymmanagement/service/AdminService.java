package com.gymmanagement.service;

import com.gymmanagement.model.Admin;
import com.gymmanagement.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public void saveAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
