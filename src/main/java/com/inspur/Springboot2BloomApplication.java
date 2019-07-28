package com.inspur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@SpringBootApplication
@EnableScheduling
public class Springboot2BloomApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2BloomApplication.class, args);
	}

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(10);			//最大空闲连接数
		jedisPoolConfig.setMaxTotal(100);		//最大连接数
		jedisPoolConfig.setMaxWaitMillis(3000);	//最长等待时间
		jedisPoolConfig.setTestOnBorrow(true);
		return jedisPoolConfig;
	}

	@Bean
	public JedisPool jedisPool() {
		JedisPool jedisPool = new JedisPool(jedisPoolConfig(),"127.0.0.1", 6379);
		return  jedisPool;
	}

}
