package zxl.netty.command.impl;

import io.netty.channel.Channel;
import zxl.netty.command.ConsoleCommand;
import zxl.netty.command.consolecommand.CreateGroupConsoleCommand;
import zxl.netty.command.consolecommand.GroupMessageConsoleCommand;
import zxl.netty.command.consolecommand.LogoutConsoleCommand;
import zxl.netty.command.consolecommand.SendToUserConsoleCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleCommandManager implements ConsoleCommand {

    private Map<String,ConsoleCommand> consoleCommandMap;
    public ConsoleCommandManager(){
        consoleCommandMap=new HashMap<String, ConsoleCommand>();
        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());
        consoleCommandMap.put("sendToGroup",new GroupMessageConsoleCommand());
    }

    public void exec(Scanner scanner, Channel channel) {
        //获取第一个指令
        String command = scanner.next();

        ConsoleCommand consoleCommand=consoleCommandMap.get(command);
        if(consoleCommand!=null){
            consoleCommand.exec(scanner,channel);
        }else{
            System.out.println("指令错误！重新输入");
        }
    }
}
