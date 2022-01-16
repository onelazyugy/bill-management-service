package com.vietle.billmanagement.service;

import com.vietle.billmanagement.config.AppConfig;
import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.Status;
import com.vietle.billmanagement.util.BillManagementUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {
    private AppConfig appConfig;

    public FileService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void writeToFile(String json, String path) throws BillManagementException {
        try {
            BufferedWriter writer = appConfig.getBufferedWriter(path);
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

    public String readFromFile(String path) throws BillManagementException {
        try {
            BufferedReader reader = appConfig.getBufferedReader(path);
            String json = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            return json;
        } catch (IOException ioe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), ioe.getMessage(), ioe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        } catch (Exception e) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), e.getMessage(), e.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }
}
