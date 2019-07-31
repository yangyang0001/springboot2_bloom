package com.inspur.product.controller;

import com.inspur.product.entity.Product;
import com.inspur.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * Order: YANG
 * Date: 2019/6/13-3:09
 * Description: No Description
 * <p>
 * TODO : 特别注意: 乐观锁在查询过修改少的情况下使用, 悲观锁基于修改多查询少的情况下使用,但是很耗CPU
 * TODO : 下图表示,MySQL中锁的 互斥与兼容的情况
 * -------------------------------------------------------------------------------------------
 * |                |               |               |              |             |
 * |                |    共享锁      |   　排他锁 　　 |   意向共享锁　 |   意向排他锁　|
 * |                |               |               |              |             |
 * -------------------------------------------------------------------------------------------
 * |                |               |               |              |             |
 * |      共享锁     |     兼容    　 |     冲突    　 |     兼容    　|     冲突   　|
 * |                |               |               |              |             |
 * -------------------------------------------------------------------------------------------
 * |                |               |               |              |             |
 * |      排他锁     |     冲突    　 |     冲突    　 |     冲突    　|     冲突   　|
 * |                |               |               |              |             |
 * -------------------------------------------------------------------------------------------
 * |                |               |               |              |             |
 * |     意向共享锁　 |     兼容    　 |     冲突    　 |     兼容    　|     兼容   　|
 * |                |               |               |              |             |
 * -------------------------------------------------------------------------------------------
 * |                |               |               |              |             |
 * |     意向排他锁 　|     冲突    　 |     冲突    　 |     兼容    　|     兼容   　|
 * |                |               |               |              |             |
 * -------------------------------------------------------------------------------------------
 *
 * <p>
 * 测试链接:
 * Jmeter 压测链接
 * http://localhost/getProductByProductNo?productNo=AK-88569
 * http://localhost/killProduct?productNo=AK-88569&killNum=${killNum}
 * http://localhost/killProductWithVersion?productNo=AK-88569&killNum=${killNum}
 * http://localhost/killProductWithBeiGuanLock?productNo=AK-88569&killNum=${killNum}
 */
@RestController
@Slf4j
public class ProductController {

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private ProductService productService;

	@Autowired
	private RedissonClient redissonClient;

	/**
	 * 获取商品的具体信息!
	 *
	 * @param productNo
	 * @return
	 */
	@RequestMapping("/getProductByProductNo")
	public Product getProductByProductNo(String productNo) {
		return productService.getProductByProductNo(productNo);
	}

	/**
	 * 秒杀场景1:直接操作数据库!,Jmeter传递过来的值为字符串,所以这里转换了一下,没有深入研究Jmeter
	 * 同时100个线程访问出现了错误!
	 *
	 * @return
	 */
	@RequestMapping("/killProduct")
	public int killProduct(String productNo, String killNum) {
		Product product = new Product();
		product.setProductNo(productNo);
		product.setKillNum(Integer.valueOf(killNum));
		return productService.minusProduct(product);
	}

	/**
	 * 秒杀场景2: MYSQL的乐观锁实现
	 * 通过Version字段来控制,直接操作数据库!,Jmeter传递过来的值为字符串,所以这里转换了一下,没有深入研究Jmeter
	 *
	 * @param productNo
	 * @param killNum
	 * @return
	 */
	@RequestMapping("/killProductWithVersion")
	public int killProductWithVersion(String productNo, String killNum) {
		Product product = new Product();
		product.setProductNo(productNo);
		product.setKillNum(Integer.valueOf(killNum));
		return productService.minusProductWithVersion(product);
	}

	/**
	 * 秒杀场景3: MYSQL的悲观锁实现
	 * 通过Version字段来控制,直接操作数据库!,Jmeter传递过来的值为字符串,所以这里转换了一下,没有深入研究Jmeter
	 *
	 * @param productNo
	 * @param killNum
	 * @return
	 */
	@RequestMapping("/killProductWithBeiGuanLock")
	public int killProductWithBeiGuanLock(String productNo, String killNum) {
		Product product = new Product();
		product.setProductNo(productNo);
		product.setKillNum(Integer.valueOf(killNum));
		return productService.killProductWithBeiGuanLock(product);
	}

	/**
	 * 秒杀场景4: Redis的原子性操作实现分布式锁
	 *
	 * @param productNo
	 * @param killNum
	 * @return
	 */
	@RequestMapping("/killWithRedis")
	public int killWithRedis(String productNo, String killNum) {
		int result = 0;
		boolean lockFlag = true;
		Jedis jedis = jedisPool.getResource();
		String lock_key = "lock_key";
		String value = UUID.randomUUID().toString() + System.currentTimeMillis();
		try {
			while (lockFlag) {
				long setNum = jedis.setnx("lock_key", value);
				if (setNum == 1) {
					lockFlag = false;
					Product product = new Product();
					product.setProductNo(productNo);
					product.setKillNum(Integer.valueOf(killNum));
					result = productService.minusProduct(product);
				} else {
					//TODO : nothing
					Thread.sleep(1000L);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if(jedis.get(lock_key).equals(value)) {
				jedis.del(lock_key);
			}
		}
		return result;
	}

	/**
	 * 基于Redisson的加锁的方式!
	 * @param productNo
	 * @param killNum
	 * @return
	 */
	@RequestMapping("/killWithRedisson")
	public int killWithRedisson(String productNo, String killNum) {
		RLock lock = redissonClient.getLock(productNo);
		int result = 0;
		try {
			if (lock!=null){
				lock.lock();
				//TODO：真正的核心处理逻辑
				Product product = new Product();
				product.setProductNo(productNo);
				product.setKillNum(Integer.valueOf(killNum));
				result = productService.minusProduct(product);
			}
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (lock!=null){
				lock.unlock();
			}
		}
		return result;
	}

}
