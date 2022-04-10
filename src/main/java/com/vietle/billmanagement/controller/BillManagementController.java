package com.vietle.billmanagement.controller;

import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.*;
import com.vietle.billmanagement.service.BillManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BillManagementController {
    private BillManagementService billManagementService;

    public BillManagementController(BillManagementService billManagementService) {
        this.billManagementService = billManagementService;
    }

    /**
     * Login
     * @param loginRequest
     * @return
     * @throws BillManagementException
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) throws BillManagementException {
//        try {
//            Thread.sleep(3 * 1000);
//        } catch (InterruptedException ie) {
//            Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), "some message", "some stacktrace", BillManagementUtil.getTimestamp());
//            throw new BillManagementException(status);
//        }
        return billManagementService.login(loginRequest);
//        Status status = BillManagementUtil.getStatus(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), UUID.randomUUID().toString(), "some message", "some stacktrace", BillManagementUtil.getTimestamp());
//        throw new BillManagementException(status);
    }

    /**
     * Get all bills
     * @return
     * @throws BillManagementException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bills")
    public List<Bill> retrieveBills() throws BillManagementException {
        return this.billManagementService.retrieveBills();
    }

    /**
     * Create a new bill
     * @param bill
     * @throws BillManagementException
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createBill(@RequestBody Bill bill) throws BillManagementException {
         this.billManagementService.createBill(bill);
    }

    /**
     * Retrieve a bill given its id
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bill/{id}")
    public Bill retrieveBillById(@PathVariable int id) throws BillManagementException {
        return this.billManagementService.retrieveBill(id);
    }

    /**
     * Get the username and password based on bill id
     * @param id
     * @return
     * @throws BillManagementException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/credential/{id}")
    public Credential retrieveCredentialById(@PathVariable int id) throws BillManagementException {
        return this.billManagementService.retrieveCredentialById(id);
    }

    /**
     * Update a bill
     * @param bill bill to update
     * @param id bill id
     * @throws BillManagementException
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    public void updateBill(@RequestBody Bill bill, @PathVariable int id) throws BillManagementException {
        this.billManagementService.updateBill(bill, id);
    }
}
