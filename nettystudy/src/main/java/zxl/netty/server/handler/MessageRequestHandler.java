package zxl.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.entity.Session;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.packet.response.MessageResponsePacket;
import zxl.netty.utils.SessionUtil;

import java.util.Date;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        //拿到消息发送方的会话信息
        Session session=SessionUtil.getSession(ctx.channel());

        //通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket=new MessageResponsePacket();
        messageResponsePacket.setFormId(session.getUserId());
        messageResponsePacket.setFormUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //拿到消息接收方的channel
        Channel toUserChannel=SessionUtil.getChannel(messageRequestPacket.getToUserId());

        //将消息发送给消息接收方
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else{
            System.out.println("【"+messageRequestPacket.getToUserId()+"】不在线，发送失败！");
        }
    }

}
