package com.yuye.example.client.controller;

import com.yuye.example.client.feign.StockFeignService;
import com.yuye.example.common.entity.YuyeParams;
import java.util.Collections;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xgf
 * @date: 2023-08-14 8:47
 */
@RestController
@RequestMapping("/stock")
public class TestController {

    @Resource
    private StockFeignService stockFeignService;

//    @Resource
//    private feign.codec.Encoder encoder;
//
//    @Resource
//    private feign.codec.Decoder decoder;

    @PostMapping("/reduct")
    public String reduct(@RequestParam("name") String name){
        YuyeParams<Integer> params = new YuyeParams();
        params.setName(name);
        params.setAge(123);
        params.setData(1233);

        YuyeParams<Integer> params1 = new YuyeParams();
        params1.setName("name1");
        params1.setAge(1231);
        params1.setData(12331);
        params.setParams(Collections.singletonList(params1));
        return stockFeignService.add(params);
    }
}
