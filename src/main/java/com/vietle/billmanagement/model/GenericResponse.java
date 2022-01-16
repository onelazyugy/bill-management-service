package com.vietle.billmanagement.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericResponse {
    private Status status;
}
