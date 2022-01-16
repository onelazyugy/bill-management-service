package com.vietle.billmanagement.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Bill {
    private String accountName;
    private String company;
    private String userName;
    private String password;
    private List<String> tags;
    private String description;
}
