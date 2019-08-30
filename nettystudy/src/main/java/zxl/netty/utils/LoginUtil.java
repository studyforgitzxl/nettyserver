package zxl.netty.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import zxl.netty.attribute.Attribute;

public class LoginUtil {

    //标识登录
    public static void MarkAsLogin(Channel channel){
        channel.attr(Attribute.LOGIN).set(true);
    }

    //判断是否登录
    public static  boolean hasLogin(Channel channel){
        io.netty.util.Attribute<Boolean> loginAttr= channel.attr(Attribute.LOGIN);

        return loginAttr.get() != null;
    }
}
