package com.deepblue.bloom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * User: YANG
 * Date: 2019/6/11-21:50
 * Description: No Description
 */
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -3639908678250707352L;
    private Long orderId;
    private String orderNum;




    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }
}
