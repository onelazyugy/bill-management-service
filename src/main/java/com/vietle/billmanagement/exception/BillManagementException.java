package com.vietle.billmanagement.exception;

import com.vietle.billmanagement.model.Status;

public class BillManagementException extends Exception {
    private Status transactionStatus;

    public BillManagementException() {
        super();
    }

    public BillManagementException(Status transactionStatus) {
        super(transactionStatus.getMessage());
        this.transactionStatus = transactionStatus;
    }

    public Status getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(Status transactionStatus) {
        this.transactionStatus = transactionStatus;
    }
}
