package zxl.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.packet.request.GroupMessageRequestPacket;

public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket groupMessageRequestPacket) throws Exception {
        System.out.println(groupMessageRequestPacket.getMessage());
    }
}
