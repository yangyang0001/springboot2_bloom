package com.deepblue.product.service;

import com.deepblue.product.entity.Product;
import com.deepblue.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

/**
 * Order: YANG
 * Date: 2019/6/13-3:10
 * Description: No Description
 */
@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private JedisPool jedisPool;

	public Product getProductByProductNo(String productNo) {
		return productMapper.getProductByProductNo(productNo);
	}

	@Transactional(transactionManager = "OrderTransactionManager")
	public int minusProduct(Product product) {
		int updateCount = 0;
		Product dbProduct = productMapper.getProductByProductNo(product.getProductNo());
		if (dbProduct != null && dbProduct.getLeftNum() > 0 && dbProduct.getLeftNum() >= product.getKillNum()) {
			updateCount = productMapper.minusProduct(product);
			System.out.println("线程名称:---------------------------------------------------->:" + Thread.currentThread().getName() +
							   ",秒杀数量---------------------------------------------------->:" + product.getKillNum());
		}
		return updateCount;
	}

	@Transactional(transactionManager = "OrderTransactionManager")
	public int minusProductWithVersion(Product product) {
		int updateCount = 0;
		Product dbProduct = productMapper.getProductByProductNo(product.getProductNo());
		if (dbProduct != null && dbProduct.getLeftNum() > 0 && dbProduct.getLeftNum() >= product.getKillNum()) {
			product.setVersion(dbProduct.getVersion());
			updateCount = productMapper.minusProductWithVersion(product);
			System.out.println(Thread.currentThread().getName() + ",version:" + dbProduct.getVersion() + ",修改数量:" + updateCount);
			System.out.println("线程名称:------------------------------------------>:" + Thread.currentThread().getName() +
					           ",version------------------------------------------>:" + dbProduct.getVersion() +
					           ",秒杀数量------------------------------------------>:" + product.getKillNum());
		}
		return updateCount;
	}

	/**
	 * 悲观锁的使用:
	 *      1.查询的时候使用for update 就OK了
	 * @param product
	 * @return
	 */
	@Transactional(transactionManager = "OrderTransactionManager")
	public int killProductWithBeiGuanLock(Product product) {
		int updateCount = 0;
		Product dbProduct = productMapper.getProductByProductNoWithBeiGuanLock(product.getProductNo());
		if (dbProduct != null && dbProduct.getLeftNum() > 0 && dbProduct.getLeftNum() >= product.getKillNum()) {
			updateCount = productMapper.minusProduct(product);
			System.out.println("线程名称:---------------------------------------------------->:" + Thread.currentThread().getName() +
					           ",秒杀数量---------------------------------------------------->:" + product.getKillNum());
		}
		return updateCount;
	}
}
