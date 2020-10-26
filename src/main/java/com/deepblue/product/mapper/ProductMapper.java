package com.deepblue.product.mapper;

import com.deepblue.product.entity.Product;
import org.apache.ibatis.annotations.*;

/**
 * Order: YANG
 * Date: 2019/6/13-3:11
 * Description: No Description
 */
//@Mapper 这里不用了 在App中添加 @MapperScan 就OK了
public interface ProductMapper {

	@Update("update product_table set left_num = left_num - #{killNum} where product_no = #{productNo}")
	public int minusProduct(Product product);

	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "productNo", column = "product_no"),
			@Result(property = "leftNum", column = "left_num"),
			@Result(property = "version", column = "version")
	})
	@Select("select * from product_table p where p.product_no = #{product_no}")
	public Product getProductByProductNo(@Param("product_no") String productNo);

	@Update("update product_table set left_num = left_num - #{killNum}, version = version + 1 where product_no = #{productNo} " +
			"and version = #{version} and left_num > 0")
	public int minusProductWithVersion(Product product);

	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "productNo", column = "product_no"),
			@Result(property = "leftNum", column = "left_num"),
			@Result(property = "version", column = "version")
	})
	@Select("select * from product_table p where p.product_no = #{product_no} for update")
	public Product getProductByProductNoWithBeiGuanLock(String productNo);
}
