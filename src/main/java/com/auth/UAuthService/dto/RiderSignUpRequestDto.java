package com.auth.UAuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderSignUpRequestDto {

    private String name;

    private String phoneNumber;

    private String email;

    private String password;
}
