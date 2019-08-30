package zxl.netty.command.consolecommand;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import zxl.netty.command.ConsoleCommand;
import zxl.netty.packet.codec.PacketCodec;
import zxl.netty.packet.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入消息和userId:");
        String line=scanner.next();

        //消息格式  userId,消息
        String[] marr=line.split(",");
        if(marr.length!=2){
            System.out.println("消息格式错误，正确格式：【userId,消息】");
        }else{
            //封装发送消息
            String userId=marr[0].trim();
            String message=marr[1].trim();
            MessageRequestPacket messageRequestPacket=new MessageRequestPacket(message,userId);
            ByteBuf requestBuffer= PacketCodec.INSTANCE.encode(channel.alloc().ioBuffer(),messageRequestPacket);
            channel.writeAndFlush(requestBuffer);
        }
    }
}
