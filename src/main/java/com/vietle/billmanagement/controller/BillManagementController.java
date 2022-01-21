package com.vietle.billmanagement.controller;

import com.vietle.billmanagement.exception.BillManagementException;
import com.vietle.billmanagement.model.*;
import com.vietle.billmanagement.service.BillManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public RetrieveBillResponse retrieveBills() throws BillManagementException {
        return RetrieveBillResponse.builder().build();
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
     * Update a bill
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/edit")
    public void editBill() {

    }


}
