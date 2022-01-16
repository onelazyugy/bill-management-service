package com.vietle.billmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vietle.billmanagement.config.AppConfig;
import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.*;
import com.vietle.billmanagement.util.BillManagementUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service
public class BillManagementService {
    private AppConfig appConfig;
    private BufferedWriter writer;

    public BillManagementService(AppConfig appConfig){
        this.appConfig = appConfig;

    }

    public LoginResponse login(LoginRequest loginRequest) {
        return LoginResponse.builder().username(loginRequest.getUsername()).build();
    }

    private RetrieveBillResponse retrieveBills() {
        return null;
    }

    public void createBill(CreateRequest createRequest) throws BillManagementException {
        try {
            String json = appConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(createRequest);
            writeToFile(json);
        } catch (JsonProcessingException jpe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), jpe.getMessage(), jpe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }

    private void writeToFile(String json) throws BillManagementException {
        String pullFileNameWithPath = appConfig.getFileDirectory().concat("/").concat(appConfig.getFileName());
        try {
            writer = new BufferedWriter(new FileWriter(pullFileNameWithPath));
            writer.write(json);
            writer.close();
        } catch (IOException ioe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), ioe.getMessage(), ioe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        } catch (Exception e) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), e.getMessage(), e.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }

    }
}
