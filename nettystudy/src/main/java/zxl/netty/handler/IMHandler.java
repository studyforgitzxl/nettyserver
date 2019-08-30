package zxl.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.client.handler.CreateGroupResponseHandler;
import zxl.netty.client.handler.MessageResponseHandler;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;
import zxl.netty.server.handler.CreateGroupRequestHandler;
import zxl.netty.server.handler.GroupMessageRequestHandler;
import zxl.netty.server.handler.MessageRequestHandler;

import java.util.Map;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final  IMHandler INSTANCE=new IMHandler();

    private static Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap.put(Command.MESSAGE_REQUEST,new MessageRequestHandler());
        handlerMap.put(Command.MESSAGE_RESPONSE,new MessageResponseHandler());
        handlerMap.put(Command.CREATEGROUP_REQUEST,new CreateGroupRequestHandler());
        handlerMap.put(Command.CREATEGROUP_RESPONSE,new CreateGroupResponseHandler());
    }

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }
}
