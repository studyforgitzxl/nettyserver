package zxl.netty.packet.request;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

@Data
public class GroupMessageRequestPacket extends Packet {
    private String groupId;
    private String message;
    public Byte getCommand() {
        return Command.GROUPMESSAGE_REQUEST;
    }
}
