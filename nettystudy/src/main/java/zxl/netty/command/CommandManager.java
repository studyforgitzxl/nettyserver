package zxl.netty.command;

import lombok.Data;
import zxl.netty.packet.request.CreateGroupRequestPacket;
import zxl.netty.packet.request.GroupMessageRequestPacket;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.request.MessageRequestPacket;
import zxl.netty.packet.response.CreateGroupResponsePacket;
import zxl.netty.packet.response.LoginResponsePacket;
import zxl.netty.packet.response.MessageResponsePacket;

import java.util.HashMap;

@Data
public class CommandManager {
    public static final HashMap<Byte, Class> commandMap;
    static {
        commandMap=new HashMap<Byte,Class>();
        commandMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        commandMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);
        commandMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        commandMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);
        commandMap.put(Command.CREATEGROUP_REQUEST, CreateGroupRequestPacket.class);
        commandMap.put(Command.CREATEGROUP_RESPONSE, CreateGroupResponsePacket.class);
        commandMap.put(Command.GROUPMESSAGE_REQUEST, GroupMessageRequestPacket.class);
    }
    public static  final CommandManager INSTANCE=new CommandManager();

    public CommandManager(){}
}
