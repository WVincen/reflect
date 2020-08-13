package com.example.reflect.exercise.validate;

/**
 * @author vincent
 */
public enum IntegrationResultCode {

    UNKNOWN("-1", "未知异常"),

    PARAM_NOT_NULL_ERROR("191099", "参数不能为空"),

    PARTNER_CODE_NOT_EMPTY("191097", "企业编码不能为空"),

    ORDERIDS_NOT_EMPTY("191096", "订单号不能为空"),

    INVOICE_TITLE_NOT_EMPTY("191095", "发票抬头不能为空"),

    SOCIAL_CREDIT_CODE_NOT_EMPTY("191094", "社会信用代码不能为空"),

    ORDERS_NOT_EMPTY("191092", "需要开具发票的订单不能为空"),

    USERNAME_NOT_EMPTY("191091", "用户姓名不能为空"),

    DATETIME_NOT_EMPTY("191089", "开票时间不能为空");

    private String code;

    private String msg;

    IntegrationResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}