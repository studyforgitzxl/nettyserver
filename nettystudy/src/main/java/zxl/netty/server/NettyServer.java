package zxl.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import zxl.netty.handler.IMHandler;
import zxl.netty.handler.PacketCodecHandler;
import zxl.netty.packet.PacketDecoder;
import zxl.netty.packet.PacketEncoder;
import zxl.netty.server.handler.*;

public class NettyServer {
    private static final int PORT=9000;
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup=new NioEventLoopGroup();
        NioEventLoopGroup workerGroup =new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap
                .group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4));
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(new LoginRequestHandler());
                        //添加认证
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                        ch.pipeline().addLast(new GroupMessageRequestHandler());
                    }
                });
        serverBootstrap.attr(AttributeKey.newInstance("serverName"),"nettyServer");
        bind(serverBootstrap,PORT);
    }

    private static  void bind(final ServerBootstrap serverBootstrap,final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("端口"+port+"绑定成功！");
                }else{
                    System.out.println("端口绑定失败！");
                    bind(serverBootstrap,port+1);
                }
            }
        });
    }
}
