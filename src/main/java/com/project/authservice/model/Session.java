package com.project.authservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter@Setter
public class Session extends BaseModel{
    @Column(length = 300)   // since Mysql was not able to store token
    private String token;
    private Date expiringAt;
    private String ipAddress;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.ORDINAL)
    private SessionStatus status;
}
