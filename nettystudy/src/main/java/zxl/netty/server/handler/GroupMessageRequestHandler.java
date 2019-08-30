package zxl.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import zxl.netty.entity.Session;
import zxl.netty.packet.request.GroupMessageRequestPacket;
import zxl.netty.utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        //从Session里获取出群聊数组
        ChannelGroup channelGroup= SessionUtil.getChannelGroup(groupMessageRequestPacket.getGroupId());
        //取出用户
        List<Session> sessionList=new ArrayList<Session>();
        for(Channel channel:channelGroup){
            sessionList.add(SessionUtil.getSession(channel));
        }
        Session thisuser=SessionUtil.getSession(ctx.channel());
        //发送消息给群聊里的人
        String response="收到群【"+groupMessageRequestPacket.getGroupId()+"】里【"+thisuser.getUserId()+"："+thisuser.getUserName()+"】:"+groupMessageRequestPacket.getMessage();

        //回复响应
        GroupMessageRequestPacket responsePacket=new GroupMessageRequestPacket();
        responsePacket.setMessage(response);
        responsePacket.setGroupId(groupMessageRequestPacket.getGroupId());
        channelGroup.writeAndFlush(responsePacket);
    }
}
