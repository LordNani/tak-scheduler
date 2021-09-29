package com.simpletak.takscheduler.model.user;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "user")
public class UserEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "username", unique = true, length = 64, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ToString.Exclude
    @Column(name = "full_name", length = 128, nullable = false)
    private String fullName;



}
