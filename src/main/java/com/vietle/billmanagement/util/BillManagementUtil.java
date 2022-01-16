package com.vietle.billmanagement.util;

import com.vietle.billmanagement.model.Status;

import java.sql.Timestamp;
import java.util.Date;


public class BillManagementUtil {
    private BillManagementUtil() {}

    public static String getTimestamp() {
        return new Timestamp(new Date().getTime()).toString();
    }

    public static Status getStatus(boolean isSuccess, int statusCd, String transactionId, String message, String detailMessage, String timestamp) {
        return Status.builder().statusCd(statusCd)
                .transactionId(transactionId)
                .isSuccess(isSuccess)
                .message(message)
                .detailMessage(detailMessage)
                .timestamp(timestamp).build();
    }

//    public static HeaderInfo headerInfo(Map<String, String> headers) {
//        String transactionId = headers.get("transactionid");
//        String email = headers.get("email");
//        HeaderInfo headerInfo = HeaderInfo.builder().dapperToken(null)
//                .serviceAccountToken(null).timestamp(null)
//                .iapJwtAssertionToken(null)
//                .transactionId(transactionId).email(email).build();
//        return headerInfo;
//    }
}
