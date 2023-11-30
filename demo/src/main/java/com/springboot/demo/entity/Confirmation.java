package com.springboot.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "confirmation")

public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;


    public Confirmation(User user){
        this.user = user;
        this.createDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

}
