package com.vietle.billmanagement.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RetrieveBillResponse {
    private List<Bill> bills;
}
