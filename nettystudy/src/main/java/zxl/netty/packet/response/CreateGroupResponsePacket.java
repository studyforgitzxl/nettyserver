package zxl.netty.packet.response;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    private String groupID;
    private boolean success;
    private String message;
    private List<String> userIdList;
    private List<String> userNameList;
    public Byte getCommand() {
        return Command.CREATEGROUP_RESPONSE;
    }
}
