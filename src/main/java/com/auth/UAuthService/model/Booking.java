package com.auth.UAuthService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Booking extends BaseModel {

    //To specify it as a enum attribute. If not specified, it takes default ORDINAL
    @Enumerated(value= EnumType.STRING) //By default(ORDINAL) tinyInt it takes
    private BookingStatus bookingStatus;

    @Temporal(value= TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(value=TemporalType.TIMESTAMP)
    private Date endTime;

    private Long totalDistance;

    @ManyToOne (fetch = FetchType.LAZY)//many bookings has 1 driver; made it Lazy due to FETCHMODE impl
    private Driver driver; //every book has a driver. Currently dirver is in 1:N state, so to needle down, we need to do N:1 to booking

    @ManyToOne(fetch = FetchType.LAZY) //made it Lazy due to FETCHMODE impl
    private Rider rider;
}