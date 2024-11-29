package com.auth.UAuthService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseModel {
    @Id // this annotation makes the id property a primary key of our table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identity means auto_increment
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) //this annotation tells spring about the formats of Date object
    @CreatedDate //this tells to spring that only handle it for object creation.
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate //this tells to spring that only handle as and when update/modification happens.
    protected Date updatedAt;
}
