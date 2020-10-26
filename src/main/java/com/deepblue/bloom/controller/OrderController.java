package com.deepblue.bloom.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.deepblue.bloom.entity.Order;
import com.deepblue.bloom.entity.User;
import com.deepblue.bloom.service.OrderService;
import com.deepblue.util.RandomValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * Order: YANG
 * Date: 2019/6/13-3:09
 * Description: No Description
 *
 * TODO : 特别注意: CountingBloomFilter 目前不能用!
 *
 */
@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/insertOrder")
    public String insertOrder(){
        int insertCount = 0;
//        for(int i = 0; i < 500000; i++){
            Order order = new Order();
            order.setOrderNum(UUID.randomUUID().toString());
            insertCount += orderService.insertOrder(order);
            log.info("InsertOrderCount -------------:" + insertCount);
//        }
        return Integer.valueOf(insertCount).toString();
    }

    @RequestMapping("/insertUser")
    public String insertUser(){
        int insertCount = 0;
        RandomValue randomValue = new RandomValue();
        Random random = new Random();
        for(int i = 0; i < 100000; i++){
            User user = new User();
            user.setGender(random.nextBoolean());
            user.setPhone(randomValue.getTel());
            user.setName(randomValue.getChineseName());
            insertCount = orderService.insertUser(user);
            log.info("InsertUserCount -------------:" + insertCount);

        }
        return Integer.valueOf(insertCount).toString();
    }

    @RequestMapping("/getOrderByOrderId")
    public Order getOrderByOrderId(Long orderId){
        Order order = orderService.getOrderByOrderId(orderId);
        System.out.println("getOrderByOrderId -----------:" + order.toString());
        return order;
    }

    @RequestMapping("/getOrderListByOrderNumWithSQL")
    public List<Order> getOrderListByOrderNumWithSQL(String orderNum) {
        long startTime = System.currentTimeMillis();
        List<Order> orderList = orderService.getOrderListByOrderNumWithSQL(orderNum);
        System.err.println("getOrderListByOrderNumWithSQL 查询总耗时 ------------->:" + (System.currentTimeMillis() - startTime) + "ms");
        return orderList;
    }

    @RequestMapping("/getOrderListByOrderNumWithRedis")
    public Set<String> getOrderListByOrderNumWithRedis(String orderNum) {
        long startTime = System.currentTimeMillis();
        Set<String> orderSet = orderService.getOrderListByOrderNumWithRedis(orderNum);
        System.err.println("getOrderListByOrderNumWithRedis 查询总耗时 ----------->:" + (System.currentTimeMillis() - startTime) + "ms");
        return orderSet;
    }

    @RequestMapping("/getOrderList")
    public PageInfo<Order> getOrderList(int page, int pageSize) {
        return orderService.getOrderList(page, pageSize);
    }

    //初始化数据到redis中
    @RequestMapping("/initData2Redis")
    public void initData2Redis() {
        Jedis jedis = jedisPool.getResource();
        List<Order> orderList = orderService.getOrderList(1, 10000).getList();
        System.out.println("orderList.size --------------->:" + orderList.size());
        for(Order order : orderList){
            jedis.set(order.getOrderNum(), order.toString());
        }
    }

    @RequestMapping("/initData2BloomFilter")
    public void initData2BloomFilter() {
        List<Order> orderList = orderService.getOrderList(1, 10000).getList();
        System.out.println("orderList.size --------------->:" + orderList.size());
        BloomFilter<String> bloomFilter =
                BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")), Long.valueOf(orderList.size()), 0.0001d);

//        CountingBloomFilter countingBloomFilter =
//                new CountingBloomFilter(orderList.size(), 10, 0);

        for(Order order : orderList){
            //初始化数据进入到 BloomFilter 中
            bloomFilter.put(order.getOrderNum());
//            countingBloomFilter.add(new Key(order.getOrderNum().getBytes()));
        }


        //测试一下误判的次数,来调整上面创建的时候BloomFilter.create( )创建参数的调整
        int bloomErrorCount = 0;
        for(int i = 0; i < 10000; i++) {
            String newUUID = UUID.randomUUID().toString() + "-YangJianWei-0001";
            if(bloomFilter.mightContain(newUUID)) {
                bloomErrorCount ++;
            }
        }
        System.out.println("bloomErrorCount --------------------->:" + bloomErrorCount);

        //CountingBloomFilter 这是Hadoop中的东西,如果启动得注释掉,因为得有依赖众多的jar包,这里只是实现了而已看下
//        int countingBloomErrorCount = 0;
//        for(int i = 0; i < 10000; i++) {
//            String newUUID = UUID.randomUUID().toString() + "-YangJianWei-0001";
//            if(countingBloomFilter.approximateCount(new Key(newUUID.getBytes())) > 0) {
//                countingBloomErrorCount ++;
//            }
//        }
//        System.out.println("countingBloomErrorCount ------------->:" + countingBloomErrorCount);
    }

    /**
     * 初始化的时候用,再次启动注释掉!
     * 使用spring 初始化机制,在初始化当前类的时候 自动执行当前的方法! 在启动的时候可以自动执行,执行完成后就可以注释掉了!
     */
//    @PostConstruct
//    public void initSomeData() {
//        Jedis jedis = jedisPool.getResource();
//        List<Order> orderList = getOrderList(1, 10000).getList();
//        for(Order order : orderList){
//            jedis.set(order.getOrderNum(), order.toString());
//        }
//    }
}
