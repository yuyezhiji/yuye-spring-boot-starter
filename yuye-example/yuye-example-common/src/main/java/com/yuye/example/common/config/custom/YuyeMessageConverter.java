package com.yuye.example.common.config.custom;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yuye.example.common.entity.YuyeParams;
import io.fury.Fury;
import io.fury.Language;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

/**
 * @author: xgf
 * @date: 2023-08-14 13:46
 */
public class YuyeMessageConverter extends FastJsonHttpMessageConverter {

    @Override protected boolean canRead(MediaType mediaType) {
        return super.canRead(mediaType);
    }

    @Override protected boolean canWrite(MediaType mediaType) {
//        return super.canWrite(mediaType);
        return true;
    }
    Fury fury = Fury.builder().withLanguage(Language.JAVA)
        // Allow to deserialize objects unknown types,
        // more flexible but less secure.
//         .withSecureMode(false)
//        .withMetaContextShare(true)

        .build();

    {
        fury.register(YuyeParams.class);
    }


    @Override public Object read(Type type, Class<?> contextClass,
        HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
//
//        Type type1 = getType(type, contextClass);
//        InputStream is = inputMessage.getBody();
//        byte[] bytes = allocateBytes(1024 * 64);
//        int offset = 0;
//        for (;;) {
//            int readCount = is.read(bytes, offset, bytes.length - offset);
//            if (readCount == -1) {
//                break;
//            }
//            offset += readCount;
//            if (offset == bytes.length) {
//                byte[] newBytes = new byte[bytes.length * 3 / 2];
//                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
//                bytes = newBytes;
//            }
//        }
//        Fury fury = Fury.builder().withLanguage(Language.JAVA)
//            // Allow to deserialize objects unknown types,
//            // more flexible but less secure.
////         .withSecureMode(false)
////        .withMetaContextShare(true)
//
//            .build();
//
//        {
//            fury.register(YuyeParams.class);
//        }
//        return fury.deserialize(bytes);
        Object o =  super.read(type, contextClass, inputMessage);
        System.out.println(String.format("第%d次 read, data:%s", i++, o));
        return o;
    }

    public static Integer i = 0;

    private final static ThreadLocal<byte[]> bytesLocal = new ThreadLocal<byte[]>();

    private static byte[] allocateBytes(int length) {
        byte[] chars = bytesLocal.get();

        if (chars == null) {
            if (length <= 1024 * 64) {
                chars = new byte[1024 * 64];
                bytesLocal.set(chars);
            } else {
                chars = new byte[length];
            }
        } else if (chars.length < length) {
            chars = new byte[length];
        }

        return chars;
    }

    @Override public void write(Object o, Type type, MediaType contentType,
        HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {


        byte[] bytes = fury.serialize(o);
//        ByteArrayOutputStream outnew = new ByteArrayOutputStream();
//        outnew.write(bytes);
//        outnew.writeTo(outputMessage.getBody());


//        this.writeInternal(o, outputMessage);
//        outputMessage.getBody().write(bytes);
//        outputMessage.getBody().flush();
        System.out.println(String.format("第%d次 write, data:%s", i++, o));
        super.write(o, type, contentType, outputMessage);
    }
}
