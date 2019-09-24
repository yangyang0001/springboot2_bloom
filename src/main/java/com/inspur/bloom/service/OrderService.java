package com.inspur.bloom.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inspur.bloom.entity.Order;
import com.inspur.bloom.entity.User;
import com.inspur.bloom.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Order: YANG
 * Date: 2019/6/13-3:10
 * Description: No Description
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private JedisPool jedisPool;

    @Transactional(transactionManager = "OrderTransactionManager")
    public int insertOrder(Order order){
        int insertCount = orderMapper.insertOrder(order);
        return insertCount;
    }

    @Transactional(transactionManager = "OrderTransactionManager")
    public int insertUser(User user) {
        int insertCount = orderMapper.insertUser(user);
        return insertCount;
    }

    public Order getOrderByOrderId(Long orderId) {
        return orderMapper.getOrderByOrderId(orderId);
    }

    public PageInfo<Order> getOrderList(int page, int pageSize){
        PageHelper.startPage(page, pageSize);
        List<Order> orderList = orderMapper.getOrderList();
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        return pageInfo;
    }

    public List<Order> getOrderListByOrderNumWithSQL(String orderNum) {
        List<Order> orderList = orderMapper.getOrderListByOrderNumWithSQL(orderNum);
        return orderList;
    }

    public Set<String> getOrderListByOrderNumWithRedis(String orderNum) {
        Set<String> orderSet = jedisPool.getResource().keys("*" + orderNum + "*");
        return orderSet;
    }


}
