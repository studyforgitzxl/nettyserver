package zxl.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.packet.response.CreateGroupResponsePacket;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        System.out.println("群创建成功,id为【"+createGroupResponsePacket.getGroupID()+"】");
        System.out.println("群里面有："+createGroupResponsePacket.getUserNameList()+"");
    }
}
