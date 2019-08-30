package zxl.netty.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+":客户端写出数据");

        for (int i=0;i<1000;i++){
            //获取数据
            ByteBuf buffer=getByteBuf(ctx);

            ctx.channel().writeAndFlush(buffer);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx){
        //获取二进制抽象 BYTEBUF
        ByteBuf byteBuf=ctx.alloc().buffer();
        //准备数据，指定字符串字符集为utf-8
        byte[] bytes="你好，欢迎关注我的微信公众号，《灭亡者的博客》".getBytes(Charset.forName("UTF-8"));

        //填充数据到bytebuf
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf=(ByteBuf)msg;
        System.out.println(new Date()+"：接收到服务端数据--> "+byteBuf.toString(Charset.forName("UTF-8")));
    }
}
