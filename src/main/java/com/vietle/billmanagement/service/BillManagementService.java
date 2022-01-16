package com.vietle.billmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vietle.billmanagement.config.AppConfig;
import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.*;
import com.vietle.billmanagement.util.BillManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class BillManagementService {
    private AppConfig appConfig;
    private FileService fileService;

    public BillManagementService(AppConfig appConfig, FileService fileService){
        this.appConfig = appConfig;
        this.fileService = fileService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return LoginResponse.builder().username(loginRequest.getUsername()).build();
    }

    private RetrieveBillResponse retrieveBills() {
        return null;
    }

    public void createBill(CreateRequest createRequest) throws BillManagementException {
        try {
            String currentJsonFromFile = appConfig.getFileDirectory().concat("/").concat(appConfig.getFileName());
            String jsonFromFile = readFromFile(currentJsonFromFile);
            log.info(jsonFromFile);
            //convert jsonFromFile to a list of CreateRequest and add the createRequest then write it back to the file

            String json = appConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(createRequest);
            writeToFile(json, currentJsonFromFile);
        } catch (JsonProcessingException jpe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), jpe.getMessage(), jpe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        } catch (Exception e) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), e.getMessage(), e.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }

    private void writeToFile(String json, String currentJsonFromFile) throws BillManagementException {
        fileService.writeToFile(json, currentJsonFromFile);
    }


    private String readFromFile(String path) throws BillManagementException {
        return fileService.readFromFile(path);
    }
}
