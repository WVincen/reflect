package com.example.reflect.exercise.validate;

import lombok.Data;

/**
 * @author vincent
 */
@Data
public class FieldError {
    private String fieldName;
    private String errorMsg;
    private IntegrationResultCode resultCode;
}