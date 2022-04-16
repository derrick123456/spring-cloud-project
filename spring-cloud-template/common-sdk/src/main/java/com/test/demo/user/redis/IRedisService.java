package com.test.demo.user.redis;

import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: common-base </p> 
 * <p>文件名称: IRedisService.java </p> 
 * <p>类型描述: [redis操作服务类] </p>
 * @author wgg
 * @version V1.0
 */
public interface IRedisService {

	/**
	 * <p>功能描述: [对应key-value存入缓存] </p>
	 * @Title set
	 * @param key .
	 * @param value .
	 * @return boolean
	 * */
	public boolean set(final String key, Object value);

	/**
	 * <p>功能描述: [发送订阅消息] </p>
	 * @Title sendNotice
	 * @param subject .
	 * @param msg .
	 * @return boolean
	 */
	public boolean sendNotice(String subject, String msg);

	/**
	 * <p>功能描述: [存入缓存并设置时效] </p>
	 * @Title set
	 * @param key .
	 * @param value .
	 * @param expireTime .
	 * @return boolean
	 */
	public boolean set(final String key, Object value, Long expireTime);

	/**
	 * <p>功能描述: [删除] </p>
	 * @Title remove
	 * @param keys .
	 */
	public void remove(final String... keys);

	/**
	 * <p>功能描述: [修改key名 如果不存在该key或者没有修改成功返回false] </p>
	 * @Title renameInAbsent
	 * @param oldKey .
	 * @param newKey .
	 * @return Boolean
	 */
	public Boolean renameInAbsent(String oldKey, String newKey);

	/**
	 * <p>功能描述: [判断该key是否存在] </p>
	 * @Title exists
	 * @param key .
	 * @return boolean
	 */
	public boolean exists(final String key);

	/**
	 * <p>功能描述: [获取对应key的value] </p>
	 * @Title get
	 * @param key .
	 * @return Object
	 */
	public Object get(final String key);

	/**
	 * <p>功能描述: [缓存hash数据] </p>
	 * @Title hmSet
	 * @param key .
	 * @param hashKey .
	 * @param value 
	 */
	public void hmSet(String key, Object hashKey, Object value);

	/**
	 * <p>功能描述: [缓存hash对应key的值的数量] </p>
	 * @Title hSize
	 * @param key .
	 * @return long
	 */
	public long hSize(String key);

	/**
	 * <p>功能描述: [获取hash数据] </p>
	 * @Title hmGet
	 * @param key .
	 * @param hashKey .
	 * @return Object
	 */
	public Object hmGet(String key, Object hashKey);

	/**
	 * <p>功能描述: [移除hash数据] </p>
	 * @Title hmRemove
	 * @param key .
	 * @param hashKey .
	 * @return Object
	 */
	public Object hmRemove(String key, Object hashKey);

	/**
	 * <p>功能描述: [获取所有hash数据] </p>
	 * @Title hmGetAll
	 * @param key .
	 * @return List
	 */
	public List<Object> hmGetAll(String key);

	/**
	 * <p>功能描述: [列表添加] </p>
	 * @Title lPush
	 * @param key .
	 * @param value .
	 */
	public void lPush(String key, Object value);

	/**
	 * <p>功能描述: [获取所有列表] </p>
	 * @Title lGetAll
	 * @param key .
	 * @return List
	 */
	public List<Object> lGetAll(String key);

	/**
	 * <p>功能描述: [集合] </p>
	 * @Title add
	 * @param key .
	 * @param value .
	 */
	public void add(String key, Object value);

	/**
	 * <p>功能描述: [获取集合] </p>
	 * @Title setMembers
	 * @param key .
	 * @return Set .
	 */
	public Set<Object> setMembers(String key);

	/**
	 * <p>功能描述: [有序集合] </p>
	 * @Title zAdd
	 * @param key .
	 * @param value .
	 * @param scoure .
	 */
	public void zAdd(String key, Object value, double scoure);

	/**
	 * <p>功能描述: [有序集合获取] </p>
	 * @Title rangeByScore
	 * @param key .
	 * @param scoure .
	 * @param scoure1 .
	 * @return Set
	 */
	public Set<Object> rangeByScore(String key, double scoure, double scoure1);

}
