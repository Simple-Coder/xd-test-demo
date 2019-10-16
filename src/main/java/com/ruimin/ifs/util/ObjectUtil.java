package com.ruimin.ifs.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author xiedong
 * 对象序列化与反序列化工具类
 * @date 2019/7/23 19:12
 */
@Slf4j
public class ObjectUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * 对象序列化方法
     *
     * @param object 传入的对象
     * return 该对象的序列化字符串
     */
    public static String Serialize(Object object){
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象序列化出现问题",e);
        }
        return null;
    }
    /**
     * 对象反序列化方法
     *
     * @param str 字符串
     * @param clz 字符串所对应的对象字节码
     * return 对象
     */
    public static<T> T Deserialize(String str,Class<T> clz){
        try {
           return (T) mapper.readValue(str,clz);
        } catch (IOException e) {
            log.error("对象反序列化出现问题",e);
        }
        return null;
    }
}
