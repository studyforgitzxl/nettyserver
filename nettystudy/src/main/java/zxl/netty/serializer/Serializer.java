package zxl.netty.serializer;

import zxl.netty.serializer.impl.JSONSerializer;

public interface Serializer {
    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT=new JSONSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换为二进制
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 二进制转为java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
