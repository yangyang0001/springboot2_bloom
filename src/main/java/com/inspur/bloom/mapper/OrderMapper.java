package com.inspur.bloom.mapper;

import com.inspur.bloom.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Order: YANG
 * Date: 2019/6/13-3:11
 * Description: No Description
 */
//@Mapper 这里不用了 在App中添加 @MapperScan 就OK了
public interface OrderMapper {


	@Insert("insert into bloom_table (order_num) values (#{orderNum})")
	public int insertOrder(Order order);


	@Results({
		@Result(property = "orderId", column = "order_id"),
		@Result(property = "orderNum", column = "order_num")
	})
	@Select("select * from bloom_table")
	public List<Order> getOrderList();



	@Results({
		@Result(property = "orderId", column = "order_id"),
		@Result(property = "orderNum", column = "order_num")
	})
	@Select("select * from bloom_table b where b.order_id = #{order_id}")
	public Order getOrderByOrderId(@Param("order_id") Long order_id);

	@Results({
			@Result(property = "orderId", column = "order_id"),
			@Result(property = "orderNum", column = "order_num")
	})
	@Select("select * from bloom_table b where b.order_num like CONCAT('%',#{order_num},'%')")
	List<Order> getOrderListByOrderNumWithSQL(@Param("order_num") String orderNum);

}
