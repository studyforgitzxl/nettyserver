package zxl.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.Packet;
import zxl.netty.packet.codec.PacketCodec;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.packet.response.LoginResponsePacket;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //服务端处理登录请求
        ByteBuf requestBuffer=(ByteBuf)msg;
        //解码
        Packet packet= PacketCodec.INSTANCE.decode(requestBuffer);

        //判断是否是登录请求包
        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket=(LoginRequestPacket)packet;
            LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            //登录校验
            if(valid(loginRequestPacket)){
                //校验成功
                loginResponsePacket.setSuccess(true);
            }else{
                //校验失败
                loginResponsePacket.setReason("账号密码数据校验失败");
                loginResponsePacket.setSuccess(false);
            }
            //编码
            ByteBuf responseBuffer=PacketCodec.INSTANCE.encode(ctx.alloc().ioBuffer(),loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuffer);
        }else if(packet instanceof MessageRequestPacket){
            //解码
            MessageRequestPacket messageRequestPacket=(MessageRequestPacket)packet;
            System.out.println(new Date()+"：收到客户端消息-->"+messageRequestPacket.getMessage());

            //回复消息
            MessageRequestPacket messageResponsePacket=new MessageRequestPacket();
            messageResponsePacket.setMessage("服务端回复：【"+messageRequestPacket.getMessage()+"】");
            ByteBuf responseBuffer=PacketCodec.INSTANCE.encode(ctx.alloc().ioBuffer(),messageResponsePacket);
            ctx.channel().writeAndFlush(responseBuffer);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
