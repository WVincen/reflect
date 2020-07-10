package com.example.reflect.exercise;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author vincent
 */
public class ReflectExerciseDemo {

    @Data
    public static class PersonalInfoDto {

        @TransferETC(name = "姓名")
        private String name;

        @TransferETC(name = "年纪")
        private Integer age;

        @TransferETC(name = "性别")
        private String gender;

        @TransferETC(name = "住址")
        private String address;

        private String hobby;
    }


    @Data
    private static class Desc {
        private String fieldName;

        private String newOne;

        private String oldOne;
    }

    /**
     * 比较两个对象中不同的属性值
     *
     * @param t1
     * @param t2
     * @param <T>
     * @return
     */
    private <T> List<Desc> compareTo(T t1, T t2) {
        return Arrays.stream(t1.getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        return !Objects.equals(field.get(t1), field.get(t2));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .filter(item -> item.isAnnotationPresent(TransferETC.class))
                .map(field -> {
                    try {
                        Desc desc = new Desc();
                        desc.setFieldName(field.getName());
                        desc.setNewOne(field.get(t1).toString());
                        desc.setOldOne(field.get(t2).toString());
                        TransferETC annotation = field.getAnnotation(TransferETC.class);
                        Optional.ofNullable(annotation).map(TransferETC::name).filter(StringUtils::isNotEmpty).ifPresent(desc::setFieldName);
                        return desc;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @Test
    public void t() {
        PersonalInfoDto infoDto = new PersonalInfoDto();
        infoDto.setName("小囧");
        infoDto.setAge(20);
        infoDto.setGender("男");
        infoDto.setAddress("上海");
        infoDto.setHobby("篮球");

        PersonalInfoDto infoDto2 = new PersonalInfoDto();
        infoDto2.setName("小乐");
        infoDto2.setAge(21);
        infoDto2.setGender("男");
        infoDto2.setAddress("北京");
        infoDto2.setHobby("足球");

        List<Desc> descs = compareTo(infoDto, infoDto2);
        System.out.println(JSON.toJSONString(descs, SerializerFeature.PrettyFormat));
    }

    /**
     * 把对象的属性名转化成中文展示
     *
     * @param t
     * @param <T>
     * @return
     */
    private <T> String transferFields(T t) {
        return Arrays.stream(t.getClass().getDeclaredFields())
                .filter(item -> item.isAnnotationPresent(TransferETC.class))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return String.format("%s：%s", field.getAnnotation(TransferETC.class).name(), field.get(t));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.joining("；"));
    }

    @Test
    public void t2() {
        PersonalInfoDto infoDto = new PersonalInfoDto();
        infoDto.setName("小草");
        infoDto.setAge(18);
        infoDto.setGender("女");
        infoDto.setAddress("上海");
        infoDto.setHobby("跑步");

        String transferFields = transferFields(infoDto);
        System.out.println(transferFields);
    }
}
