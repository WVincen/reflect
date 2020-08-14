package com.example.reflect.exercise.validate;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author vincent
 */
public class ValidateTest {

    @Test
    public void t() {
        InvoiceRequestQueryApiDto queryApiDto = new InvoiceRequestQueryApiDto();
        InvoiceRequestQueryApiDto.Orders orders = new InvoiceRequestQueryApiDto.Orders();
        queryApiDto.setOrders(Lists.newArrayList(orders));

        List<FieldError> check = Validate.check(queryApiDto);
        check.forEach(System.out::println);
    }

    @Test
    public void t2(){
        InvoiceRequestQueryApiDto queryApiDto = new InvoiceRequestQueryApiDto();
        InvoiceRequestQueryApiDto.Orders orders = new InvoiceRequestQueryApiDto.Orders();
        queryApiDto.setOrders(Lists.newArrayList(orders));

        List<FieldError> check = VavrValidate.check(queryApiDto);
        check.forEach(System.out::println);
    }
}
