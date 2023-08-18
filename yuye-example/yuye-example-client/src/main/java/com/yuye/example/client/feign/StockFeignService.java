package com.yuye.example.client.feign;

import com.yuye.example.common.entity.YuyeParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * name 指定调用rest接口对应服务名
 * path 指定调用接口所在Controller的@RequestMapping对应路径，没有则不填
 */
@FeignClient(name = "stock-service", path = "/stock",url = "http://localhost:8880")
public interface StockFeignService {
    // 声明需要调用的接口方法名
    @PostMapping("/reduct")
    String add(@RequestBody YuyeParams params);
}
