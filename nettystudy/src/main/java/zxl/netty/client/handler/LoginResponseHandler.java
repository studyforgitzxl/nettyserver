package zxl.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.packet.response.LoginResponsePacket;
import zxl.netty.utils.LoginUtil;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        //客户端接收服务端登录响应
        if(loginResponsePacket.isSuccess()){
            LoginUtil.MarkAsLogin(ctx.channel());
            System.out.println(new Date()+"：用户【"+loginResponsePacket.getUserId()+"】登录成功！");
        }else{
            System.out.println(new Date()+"：用户【"+loginResponsePacket.getUserId()+"】登录失败，原因是："+loginResponsePacket.getReason());
        }
    }
}
