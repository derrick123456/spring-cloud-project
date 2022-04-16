package com.test.demo.user.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wgg
 * @date 2021/04/05
 */
@Slf4j
//@Service
public class RedisServiceImpl implements IRedisService {

	/**
	 * <p>字段描述:[列表查询第一个下标]</p>
	 * @Fields INDEX_FIRST : 
	 */
	private static final long INDEX_FIRST = 0;

	/**
	 * <p>字段描述:[列表查询倒数第一个下标]</p>
	 * @Fields INDEX_LAST : 
	 */
	private static final long INDEX_LAST = -1;

	@Autowired
	private RedisTemplate redisTemplate;




	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.idea.platform.register.service.IRedisService#sendNotice(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public boolean sendNotice(String subject, String msg) {
		redisTemplate.convertAndSend(subject, msg);
		return true;
	}

	@Override
	public boolean set(String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			log.error("RedisServiceImpl.set", e);
		}
		return result;
	}

	@Override
	public boolean set(String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate
					.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			log.error("RedisServiceImpl.set", e);
		}
		return result;
	}

	@Override
	public void remove(String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	@Override
	public Boolean renameInAbsent(String oldKey, String newKey) {
		if (redisTemplate.hasKey(newKey)) {
			remove(newKey);
		}
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	/**
	 * 删除对应的value
	 * @param key
	 */
	private void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	@Override
	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public Object get(String key) {
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		return operations.get(key);
	}

	@Override
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate
				.opsForHash();
		hash.put(key, hashKey, value);
	}

	@Override
	public long hSize(String key) {
		HashOperations<String, Object, Object> hash = redisTemplate
				.opsForHash();
		return hash.size(key);
	}

	@Override
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate
				.opsForHash();
		return hash.get(key, hashKey);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.idea.platform.register.service.IRedisService#hmRemove(java.lang.
	 * String, java.lang.Object)
	 */
	@Override
	public Object hmRemove(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate
				.opsForHash();
		return hash.delete(key, hashKey);
	}

	@Override
	public List<Object> hmGetAll(String key) {
		HashOperations<String, Object, Object> hash = redisTemplate
				.opsForHash();
		Set<Object> keys = hash.keys(key);
		return hash.multiGet(key, keys);
	}

	@Override
	public void lPush(String key, Object value) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(key, value);
	}

	@Override
	public List<Object> lGetAll(String key) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(key, INDEX_FIRST, INDEX_LAST);
	}

	@Override
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	@Override
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	@Override
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	@Override
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}




}
