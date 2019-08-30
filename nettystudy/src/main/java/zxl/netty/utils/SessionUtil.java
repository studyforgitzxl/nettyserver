package zxl.netty.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.w3c.dom.Attr;
import zxl.netty.attribute.Attribute;
import zxl.netty.entity.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    //userId --> Channel的映射
    private static final Map<String, Channel> userIdChannelMap=new ConcurrentHashMap<String, Channel>();
    private static final Map<String, ChannelGroup> groupMap=new ConcurrentHashMap<String, ChannelGroup>();

    public static void bindSession(Session session, Channel channel){
        userIdChannelMap.put(session.getUserId(),channel);
        channel.attr(Attribute.SESSION).set(session);
    }

    public static void unBindSession(Channel channel){
        if(hasLogin(channel)){
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attribute.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attribute.SESSION);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attribute.SESSION).get();
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static void setChannelGroup(String groupId,ChannelGroup channelGroup){
        groupMap.put(groupId,channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId){
        return groupMap.get(groupId);
    }
}
