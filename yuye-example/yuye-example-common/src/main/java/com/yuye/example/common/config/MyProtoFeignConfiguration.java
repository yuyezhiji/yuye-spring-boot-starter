package com.yuye.example.common.config;

import com.yuye.example.common.config.custom.YuyeMessageConverter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyProtoFeignConfiguration {
    //Autowire the message converters.
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

//    override the encoder
//    @Bean
//    @ConditionalOnMissingBean
//    public feign.codec.Encoder springEncoder() {
//        return new SpringEncoder(this.messageConverters);
//    }
//
//    //override the encoder
    @Bean
    @ConditionalOnMissingBean
    public feign.codec.Decoder springDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConverters));
    }


    /**
     * protobuf 序列化
     */
    @Bean
    HttpMessageConverter protobufHttpMessageConverter() {
        return new YuyeMessageConverter();
    }

    /**
     * protobuf 反序列化
     */
    @Bean
    RestTemplate restTemplate(YuyeMessageConverter protobufHttpMessageConverter) {
        return new RestTemplate(Collections.singletonList(protobufHttpMessageConverter));
    }
}