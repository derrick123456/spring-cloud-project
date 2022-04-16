package com.test.demo.sys.feign;

import com.test.demo.user.model.CommonRsp;
import com.test.demo.sys.model.entity.TestModuleManage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeignService {

    @GetMapping(value = "/module/test")
    CommonRsp<String> test();

    @GetMapping(value = "/module/{id}")
    CommonRsp<TestModuleManage> info(@RequestParam(value = "id")  String id);
}
