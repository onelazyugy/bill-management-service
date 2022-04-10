package com.vietle.billmanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vietle.billmanagement.config.AppConfig;
import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.*;
import com.vietle.billmanagement.util.BillManagementUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    /**
     * retrieve all bills from file
     * @return
     * @throws BillManagementException
     */
    public List<Bill> retrieveBills() throws BillManagementException {
        List<Bill> billsFromFile = new ArrayList<>();
        try {
            String filePath = appConfig.getFileDirectory().concat("/").concat(appConfig.getFileName());
            String jsonString = readFromFile(filePath);

            if(jsonString != null || !jsonString.isEmpty()) {
                billsFromFile = Arrays.asList(appConfig.getObjectMapper().readValue(jsonString, Bill[].class));
            }
            return billsFromFile;
        } catch (JsonProcessingException jpe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), jpe.getMessage(), jpe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        } catch (Exception e) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), e.getMessage(), e.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }

    /**
     * retrieve a single bill based on id
     * @param id
     * @return
     * @throws BillManagementException
     */
    public Bill retrieveBill(int id) throws BillManagementException {
        Optional<Bill> bill = this.retrieveBills().stream().filter(b -> b.getKey() == id).findFirst();
        if(bill.isPresent()) {
            return bill.get();
        }
        return Bill.builder().build();
    }

    /**
     * read from file and add a new bill
     * @param bill
     * @throws BillManagementException
     */
    public void createBill(Bill bill) throws BillManagementException {
        try {
            List<Bill> bills = new ArrayList<>(1);
            List<Bill> billsFromFile = new ArrayList<>();

            String filePath = appConfig.getFileDirectory().concat("/").concat(appConfig.getFileName());
            String jsonString = readFromFile(filePath);

            if(jsonString != null || !jsonString.isEmpty()) {
                billsFromFile = Arrays.asList(appConfig.getObjectMapper().readValue(jsonString, Bill[].class));
            }
            int latestId = getId(billsFromFile);
            if(latestId == 0) {
                bill.setKey(0);
            } else {
                bill.setKey(latestId);
            }
            bills.add(bill);
            List<Bill> combinedBills = Stream.concat(bills.stream(), billsFromFile.stream()).collect(Collectors.toList());
            String json = appConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(combinedBills);
            writeToFile(json, filePath);
        } catch (JsonProcessingException jpe) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), jpe.getMessage(), jpe.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        } catch (Exception e) {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), e.getMessage(), e.getStackTrace().toString(), BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }

    public void updateBill(Bill billToUpdate, int id) throws BillManagementException {
        Bill bill = this.retrieveBill(id);
        if(bill != null && bill.getKey() == id) {
            //replace the found bill with billToUpdate
        } else {
            Status status = BillManagementUtil.getStatus(false, HttpStatus.BAD_REQUEST.value(), UUID.randomUUID().toString(), "bad request", "requested resource does not exist", BillManagementUtil.getTimestamp());
            throw new BillManagementException(status);
        }
    }

    /**
     * Given a bill id, retrieve its username and password
     * @param id
     * @return
     * @throws BillManagementException
     */
    public Credential retrieveCredentialById(int id) throws BillManagementException{
        Optional<Bill> bill = this.retrieveBills().stream().filter(b -> b.getKey() == id).findFirst();
        if(bill.isPresent()) {
            return Credential.builder().username(bill.get().getUserName()).password(bill.get().getPassword()).build();
        }
        return Credential.builder().build();
    }

    private void writeToFile(String json, String currentJsonFromFile) throws BillManagementException {
        fileService.writeToFile(json, currentJsonFromFile);
    }


    private String readFromFile(String path) throws BillManagementException {
        return fileService.readFromFile(path);
    }

    private int getId(List<Bill> billsFromFile) {
        if(billsFromFile.size() > 0) {
            return billsFromFile.size();
        }
        return 0;
    }
}
