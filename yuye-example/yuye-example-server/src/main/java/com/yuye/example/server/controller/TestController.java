package com.yuye.example.server.controller;

import com.alibaba.fastjson.JSON;
import com.yuye.example.common.entity.YuyeParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/reduct")
    public String reduct(@RequestBody YuyeParams params){
        return JSON.toJSONString(params);
    }
}
