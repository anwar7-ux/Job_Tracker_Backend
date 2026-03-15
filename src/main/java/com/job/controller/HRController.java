package com.job.controller;

import com.job.dto.UserRequest;
import com.job.dto.UserResponse;
import com.job.service.HRService;
import com.job.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hr")
public class HRController {

    @Autowired
    private HRService hrService;

    @Autowired
    private AdminService adminService;

    // REGISTER ADMIN
    @PostMapping("/register-admin")
    public String registerAdmin(
            @RequestBody UserRequest request) {
        return hrService.registerAdmin(request);
    }

    // GET ALL USERS (HR can see everyone)
    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    // DELETE ADMIN
    @DeleteMapping("/admin/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        return hrService.deleteAdmin(id);
    }
}