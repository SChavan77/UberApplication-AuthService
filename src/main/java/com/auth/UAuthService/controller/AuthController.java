package com.auth.UAuthService.controller;

import com.auth.UAuthService.dto.RiderDto;
import com.auth.UAuthService.dto.RiderSignUpRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/signup/rider")
    public ResponseEntity<RiderDto> signUp(@RequestBody RiderSignUpRequestDto request) {
            return null;
    }


}
