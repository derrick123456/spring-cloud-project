package com.test.demo.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.demo.user.mapper.RedisConfigMapper;
import com.test.demo.user.model.entity.RedisConfig;
import com.test.demo.user.service.IRedisConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * redis配置 服务实现类
 * </p>
 *
 * @author wgg
 * @since 2022-01-06
 */
@Service
public class RedisConfigServiceImpl extends ServiceImpl<RedisConfigMapper, RedisConfig> implements IRedisConfigService {
    @Autowired
    private RedisConfigMapper redisConfigMapper;
    @Override
    public List<RedisConfig> getRedisConfigList() {
        QueryWrapper<RedisConfig> qMoQueryWrapper = new QueryWrapper<>();
      List<RedisConfig> list = redisConfigMapper.selectList(qMoQueryWrapper);
        return list;
    }
}
