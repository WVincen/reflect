package com.example.reflect.proxy;

/**
 * @author vincent
 */
public class UserServiceImpl implements UserService {
    @Override
    public void selectById() {
        System.out.println("查询 selectById");
    }

    @Override
    public void update() {
        System.out.println("更新 update");
    }
}
