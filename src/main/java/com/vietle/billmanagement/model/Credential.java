package com.vietle.billmanagement.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Credential {
    private String username;
    private String password;
}
