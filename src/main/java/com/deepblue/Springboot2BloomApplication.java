package com.deepblue;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
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
		jedisPoolConfig.setMaxIdle(100);			//最大空闲连接数
		jedisPoolConfig.setMaxTotal(500);		    //最大连接数
		jedisPoolConfig.setMaxWaitMillis(10000);	//最长等待时间
		jedisPoolConfig.setTestOnBorrow(true);
		return jedisPoolConfig;
	}

	@Bean
	public JedisPool jedisPool() {
		JedisPool jedisPool = new JedisPool(jedisPoolConfig(),"127.0.0.1", 6379);
		return  jedisPool;
	}

	/**
	 * #singleServerConfig:
	 #  idleConnectionTimeout: 10000
	 #  pingTimeout: 1000
	 #  connectTimeout: 10000
	 #  timeout: 3000
	 #  retryAttempts: 3
	 #  retryInterval: 1500
	 #  reconnectionTimeout: 3000
	 #  failedAttempts: 3
	 #  password: null
	 #  subscriptionsPerConnection: 5
	 #  clientName: null
	 #  address: "redis://127.0.0.1:6379"
	 #  subscriptionConnectionMinimumIdleSize: 1
	 #  subscriptionConnectionPoolSize: 50
	 #  connectionMinimumIdleSize: 32
	 #  connectionPoolSize: 64
	 #  database: 0
	 #  dnsMonitoring: false
	 #  dnsMonitoringInterval: 5000
	 #threads: 0
	 #nettyThreads: 0
	 #codec: !<org.redisson.codec.JsonJacksonCodec> {}
	 #"transportMode":"NIO"
	 * @return
	 */
	@Bean
	public RedissonClient redissonClient(){
		Config config=new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");
		config.useSingleServer().setIdleConnectionTimeout(10000);
		config.useSingleServer().setPingTimeout(2000);
		config.useSingleServer().setConnectTimeout(10000);
		config.useSingleServer().setTimeout(3000);
		config.useSingleServer().setRetryAttempts(3);
		config.useSingleServer().setRetryInterval(1500);
		config.useSingleServer().setReconnectionTimeout(3000);
		config.useSingleServer().setFailedAttempts(3);
		config.useSingleServer().setSubscriptionsPerConnection(5);
		config.useSingleServer().setSubscriptionConnectionMinimumIdleSize(1);
		config.useSingleServer().setSubscriptionConnectionPoolSize(50);
		config.useSingleServer().setConnectionMinimumIdleSize(32);
		config.useSingleServer().setConnectionPoolSize(500);
		RedissonClient client = Redisson.create(config);
		return client;
	}

}
