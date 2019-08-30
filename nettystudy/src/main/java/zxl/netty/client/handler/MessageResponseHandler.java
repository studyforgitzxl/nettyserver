package zxl.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.packet.response.MessageResponsePacket;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        System.out.println(new Date()+"用户【"+messageResponsePacket.getFormId()+"  "+messageResponsePacket.getFormUserName()+"】 -- > "+messageResponsePacket.getMessage());
    }
}
