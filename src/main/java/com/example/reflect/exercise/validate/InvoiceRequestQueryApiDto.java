package com.example.reflect.exercise.validate;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author vincent
 */
@Data
public class InvoiceRequestQueryApiDto implements Serializable {

    private Long partnerId;

    /**
     * 企业编码
     */
    @NotBlank(resultCode = IntegrationResultCode.PARTNER_CODE_NOT_EMPTY)
    private String partnerCode;

    /**
     * 发票抬头
     */
    @NotBlank(resultCode = IntegrationResultCode.INVOICE_TITLE_NOT_EMPTY)
    private String invoiceTitle;

    /**
     * 社会信用代码
     */
    @NotBlank(resultCode = IntegrationResultCode.SOCIAL_CREDIT_CODE_NOT_EMPTY)
    private String socialCreditCode;

    /**
     * 开票类型
     */
    private String type;

    @NotBlank(resultCode = IntegrationResultCode.ORDERS_NOT_EMPTY)
    private List<Orders> orders;

    @Data
    public static class Orders implements Serializable {
        /**
         * 需要开具发票的订单号
         */
        @NotBlank(resultCode = IntegrationResultCode.ORDERIDS_NOT_EMPTY)
        private Long orderId;
        private Travellers travellers;
    }

    @Data
    public static class Travellers implements Serializable {
        private String userName;
        private String email;
    }
}
