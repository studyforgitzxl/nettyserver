package zxl.netty.serializer.impl;

import com.alibaba.fastjson.JSON;
import zxl.netty.serializer.Serializer;
import zxl.netty.serializer.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
