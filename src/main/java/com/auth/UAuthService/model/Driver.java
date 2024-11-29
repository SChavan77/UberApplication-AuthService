package com.auth.UAuthService.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" , "bookings"})
public class Driver extends BaseModel{

    private String name;
    private String address;
    private String phoneNumber;

    @Column(nullable = true, unique = true)
    private String licenseNumber;

    //1: N : driver has many bookings/reviews
    @OneToMany(mappedBy = "driver",fetch= FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT) //means: fetch them in a batch
    private List<Booking> bookings=new ArrayList<>();
}
