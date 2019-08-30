package zxl.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zxl.netty.entity.Session;
import zxl.netty.packet.codec.PacketCodec;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.response.LoginResponsePacket;
import zxl.netty.utils.LoginUtil;
import zxl.netty.utils.SessionUtil;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket=new LoginResponsePacket();
        loginResponsePacket.setVersion(loginResponsePacket.getVersion());
        //登录校验
        if(valid(loginRequestPacket)){
            //校验成功
            //标识登录成功！
            LoginUtil.MarkAsLogin(ctx.channel());
            //将登录成功的用户保存至Session
            Session session=new Session(loginRequestPacket.getUserId(),loginRequestPacket.getUsername());
            SessionUtil.bindSession(session,ctx.channel());
            System.out.println(new Date() +"用户【"+loginRequestPacket.getUsername()+"】登录成功！！,用户id为"+loginRequestPacket.getUserId());
            loginResponsePacket.setSuccess(true);
            loginResponsePacket.setUserId(loginRequestPacket.getUserId());
        }else{
            //校验失败
            loginResponsePacket.setReason("账号密码数据校验失败");
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setUserId(loginRequestPacket.getUserId());
        }
        //编码
        ByteBuf responseBuffer=PacketCodec.INSTANCE.encode(ctx.alloc().ioBuffer(),loginResponsePacket);
        ctx.channel().writeAndFlush(responseBuffer);
    }
    private boolean valid(LoginRequestPacket loginRequestPacket){
        return true;
    }
}
