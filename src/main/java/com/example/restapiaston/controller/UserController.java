package com.example.restapiaston.controller;

import com.example.restapiaston.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/code")
    public ResponseEntity<String> getCode() {

        String code = userService.getCode();

        return ResponseEntity.ok(code);
    }
}
