package com.auth.UAuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiderDto {
    private String id;
    private String name;
    private String email;
    private String password;  //it should be encrypted password while sending.
    private String phoneNumber;
    private Date createdAt;
}
