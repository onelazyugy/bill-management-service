package com.vietle.billmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status {
    private String timestamp;
    private String message;
    @JsonIgnore
    private String detailMessage;
    private int statusCd;
    private boolean isSuccess;
    private String transactionId;
}
