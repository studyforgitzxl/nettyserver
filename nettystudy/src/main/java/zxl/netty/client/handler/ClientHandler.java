package zxl.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import zxl.netty.packet.Packet;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.codec.PacketCodec;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.packet.response.LoginResponsePacket;
import zxl.netty.utils.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+": 客户端开始登录");

        //创建登录对象
        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("flash");
        loginRequestPacket.setPassword("pwd");

        //编码
        ByteBuf buffer= PacketCodec.INSTANCE.encode(ctx.alloc().ioBuffer(),loginRequestPacket);

        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //客户端接收服务端登录响应
        ByteBuf requestBuffer = (ByteBuf)msg;
        Packet packet=PacketCodec.INSTANCE.decode(requestBuffer);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket=(LoginResponsePacket)packet;
            if(loginResponsePacket.isSuccess()){
                LoginUtil.MarkAsLogin(ctx.channel());
                System.out.println(new Date()+"：客户端登录成功！");
            }else{
                System.out.println(new Date()+"：客户端登录失败，原因是："+loginResponsePacket.getReason());
            }
        }else if(packet instanceof MessageRequestPacket){
            System.out.println("收到服务端回复消息 -- >  "+((MessageRequestPacket) packet).getMessage());
        }
    }
}
