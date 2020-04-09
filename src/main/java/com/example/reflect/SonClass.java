package com.example.reflect;

import lombok.Data;

/**
 * @author vincent
 */
@Data
public class SonClass extends FatherClass {
    private String sonName;
    protected Integer sonAge;
    public String sonBirthday;

    public String show(String sonName, Integer sonAge, String sonBirthday) throws Exception {
        return sonName + "的年龄是：" + sonAge + "，生日是：" + sonBirthday;
    }
}
