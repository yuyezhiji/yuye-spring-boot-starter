package com.yuye.example.common.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yuye.example.common.config.custom.YuyeMessageConverter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class MyWebmvcConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        YuyeMessageConverter fjc = new YuyeMessageConverter();
        FastJsonConfig fj = new FastJsonConfig();

        //字符类型字段如果为null，则输出"",而非null
        fj.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty);
        fjc.setFastJsonConfig(fj);
        converters.add(fjc);
    }
}
