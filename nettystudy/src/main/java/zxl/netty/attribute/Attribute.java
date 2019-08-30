package zxl.netty.attribute;

import io.netty.util.AttributeKey;
import zxl.netty.entity.Session;

public interface Attribute {
    AttributeKey<Boolean> LOGIN=AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION=AttributeKey.newInstance("session");
}
