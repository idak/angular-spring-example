package com.idak.tuto.api.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String email;
}
