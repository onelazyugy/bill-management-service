package com.vietle.billmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    private int key;
    private String accountName;
    private String company;
    private String userName;
    private String password;
    private List<String> tags;
    private String description;
    private boolean isFavorite;
}
