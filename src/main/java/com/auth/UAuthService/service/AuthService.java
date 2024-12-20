package com.auth.UAuthService.service;

import com.auth.UAuthService.dto.RiderDto;
import com.auth.UAuthService.dto.RiderSignUpRequestDto;

import com.auth.UAuthService.repository.RiderRepository;
import com.library.models.Rider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private RiderRepository riderRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    //Spring will inject the brcypt obejct. so to do that, we need to create a bean
    public AuthService(RiderRepository riderRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.riderRepository = riderRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public RiderDto signUpRider(RiderSignUpRequestDto riderSignUpRequestDto){
        Rider rider = Rider.builder()
                .email(riderSignUpRequestDto.getEmail())
                .name(riderSignUpRequestDto.getName())
                //.password(riderSignUpRequestDto.getPassword()) //Encrypt the password
                .password(bCryptPasswordEncoder.encode(riderSignUpRequestDto.getPassword()))
                .phoneNumber(riderSignUpRequestDto.getPhoneNumber())
                .build();

        riderRepository.save(rider);
        RiderDto response= RiderDto.from(rider);
        return response;
    }
}
