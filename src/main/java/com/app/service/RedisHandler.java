package com.app.service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHandler {
	@Autowired
	private StringRedisTemplate stringTemplate;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	public void putString(String key, long expires) {
		stringTemplate.opsForValue().set(key, "博",expires, TimeUnit.SECONDS);  // 秒
	}

	public void putString(String key) {
		stringTemplate.opsForValue().set(key, "博bo");
	}

	public String getString(String key) {
		return stringTemplate.opsForValue().get(key);
	}

	public void putObject(String key, long expires) {
		BigDecimal val = new BigDecimal("369.123");
		redisTemplate.opsForValue().set(key, val, expires, TimeUnit.MINUTES); // 分钟
	}

	public void pubObject(String key) {
		BigDecimal val = new BigDecimal("741.258");
		redisTemplate.opsForValue().set(key, val);
	}

	public BigDecimal getObject(String key) {
		Object object = redisTemplate.opsForValue().get(key);
		if (object == null) {
			return null;
		}
		return (BigDecimal) object;
	}

}
