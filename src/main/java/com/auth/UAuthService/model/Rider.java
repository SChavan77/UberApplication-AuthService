package com.auth.UAuthService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" , "bookings"})
public class Rider extends BaseModel{

   @Column(nullable = false)
    private String name;

   @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    //property name in 'many' class(booking) should be same as here mentioned in mappedBy
    @OneToMany(mappedBy = "rider") //mapping instruction : to mention this 1:N to be mapped to rider field in Booking.
    private List<Booking> bookings=new ArrayList<>();
}

