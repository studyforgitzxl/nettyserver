package zxl.netty.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf= (ByteBuf) msg;
        System.out.println(new Date()+":服务端读到数据 - > "+byteBuf.toString(Charset.forName("UTF-8")));
        ByteBuf writebuffer=getByteBuf(ctx);
        ctx.channel().writeAndFlush(writebuffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        byte[] bytes="你好，欢迎关注netty学习中心！".getBytes(Charset.forName("UTF-8"));

        ByteBuf buffer=ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
