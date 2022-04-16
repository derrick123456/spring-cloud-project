package com.test.demo.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.test.demo.user.model.entity.RedisConfig;

import java.util.List;

/**
 * <p>
 * redis配置 服务类
 * </p>
 *
 * @author wgg
 * @since 2022-01-06
 */
public interface IRedisConfigService extends IService<RedisConfig> {

    public List<RedisConfig> getRedisConfigList();
}
