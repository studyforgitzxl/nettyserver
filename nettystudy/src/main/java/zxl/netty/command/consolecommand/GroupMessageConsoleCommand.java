package zxl.netty.command.consolecommand;

import io.netty.channel.Channel;
import zxl.netty.command.ConsoleCommand;
import zxl.netty.packet.request.GroupMessageRequestPacket;

import java.util.Scanner;

public class GroupMessageConsoleCommand implements ConsoleCommand {
    public void exec(Scanner scanner, Channel channel) {

        System.out.println("请输入groupId和消息，中间用逗号分隔");
        String request=scanner.next();
        String[] arr=request.split(",");
        if(arr.length!=2){
            System.out.println("消息格式错误，重新输入");
        }else{
            GroupMessageRequestPacket groupMessageRequestPacket=new GroupMessageRequestPacket();
            groupMessageRequestPacket.setGroupId(arr[0].trim());
            groupMessageRequestPacket.setMessage(arr[1].trim());
            channel.writeAndFlush(groupMessageRequestPacket);
        }
    }
}
