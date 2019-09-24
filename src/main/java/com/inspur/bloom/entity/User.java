package com.inspur.bloom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: springboot2_bloom
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-09-05 22:35
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -4529987371047547895L;

    private Integer id;

    private String name;

    private boolean gender;

    private String phone;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                '}';
    }
}
