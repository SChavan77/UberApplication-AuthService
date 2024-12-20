package com.auth.UAuthService.dto;

import com.library.models.Rider;
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
    private Long id;
    private String name;
    private String email;
    private String password;  //it should be encrypted password while sending.
    private String phoneNumber;
    private Date createdAt;

    public static RiderDto from(Rider r){
        RiderDto result= RiderDto.builder()
                .id(r.getId())
                .name(r.getName())
                .email(r.getEmail())
                .password(r.getPassword())
                .phoneNumber(r.getPhoneNumber())
                .createdAt(r.getCreatedAt())
                .build();
        return result;
    }
}


//@Mapper: We can use Mapper Class way of mapping one obejct to another object. MapStruct is one import feature to use & implement