package zxl.netty.command.consolecommand;

import io.netty.channel.Channel;
import zxl.netty.command.ConsoleCommand;
import zxl.netty.packet.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    //定义分隔符
    private static final String USER_ID_SPLITER=",";
    public void exec(Scanner scanner, Channel channel) {
        //创建群聊
        System.out.println("【拉人群聊】输入userId列表，userId之间用英文逗号分隔：");
        String userIds=scanner.next();

        CreateGroupRequestPacket createGroupRequestPacket=new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
