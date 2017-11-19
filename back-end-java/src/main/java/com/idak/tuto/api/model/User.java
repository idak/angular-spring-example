package com.idak.tuto.api.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String username;
    private String password;
    private String email;

}
