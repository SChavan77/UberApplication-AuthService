package com.auth.UAuthService.controller;

import com.auth.UAuthService.dto.RiderDto;
import com.auth.UAuthService.dto.RiderSignUpRequestDto;
import com.auth.UAuthService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/rider")
    public ResponseEntity<RiderDto> signUp(@RequestBody RiderSignUpRequestDto request) {
           RiderDto response= authService.signUpRider(request);
           return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/signin/rider")
    public ResponseEntity<?> signIn(){
        return new ResponseEntity<>(10,HttpStatus.CREATED);
    }

}
