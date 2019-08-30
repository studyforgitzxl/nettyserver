package zxl.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import zxl.netty.client.handler.CreateGroupResponseHandler;
import zxl.netty.client.handler.GroupMessageResponseHandler;
import zxl.netty.client.handler.LoginResponseHandler;
import zxl.netty.client.handler.MessageResponseHandler;
import zxl.netty.command.impl.ConsoleCommandManager;
import zxl.netty.handler.IMHandler;
import zxl.netty.handler.PacketCodecHandler;
import zxl.netty.packet.PacketDecoder;
import zxl.netty.packet.PacketEncoder;
import zxl.netty.packet.codec.PacketCodec;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.utils.LoginUtil;
import zxl.netty.utils.SessionUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST="127.0.0.1";
    private static final int PORT=9001;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup=new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,7,4));
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                        ch.pipeline().addLast(new GroupMessageResponseHandler());
                    }
                });
        bootstrap.attr(AttributeKey.newInstance("clientName"),"nettyClient");
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }

    private static void connect(final Bootstrap bootstrap, final String host, final int port, final int retry){
        //建立连接
        bootstrap.connect(host,port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("连接成功！");

                    Channel channel=((ChannelFuture)future).channel();
                    //启动控制台线程
                    startConsoleThread(channel);
                }else if(retry==0){
                    System.out.println("重试次数已用完，放弃连接！");
                }else{
                    //第几次重新连接
                    int order=(MAX_RETRY-retry)+1;
                    //本次重连的间隔
                    int delay=1000;
                    System.out.println(new Date()+"：连接失败，第"+order+"次重连......");
                    bootstrap.config().group().schedule(new Runnable() {
                        public void run() {
                            connect(bootstrap,host,port,retry-1);
                        }
                    }, delay, TimeUnit.SECONDS);
                }
            }
        });
    }

    private static void startConsoleThread(final Channel channel){
        new Thread(new Runnable() {
            public void run() {
                while (!Thread.interrupted()){
                    if(LoginUtil.hasLogin(channel)){
                        //判断是否登录
                        System.out.println("输入消息发送至服务器");
                        Scanner sc=new Scanner(System.in);
                        ConsoleCommandManager consoleCommandManager=new ConsoleCommandManager();
                        consoleCommandManager.exec(sc,channel);
                    }else{
                        //发送登录请求
                        System.out.println("请输入用户名：");
                        Scanner scanner=new Scanner(System.in);
                        String username=scanner.nextLine();

                        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
                        loginRequestPacket.setUserId(UUID.randomUUID().toString());
                        loginRequestPacket.setUsername(username);
                        loginRequestPacket.setPassword("pwd");

                        channel.writeAndFlush(loginRequestPacket);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
