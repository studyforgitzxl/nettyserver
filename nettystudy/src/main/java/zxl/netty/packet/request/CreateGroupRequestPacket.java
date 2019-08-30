package zxl.netty.packet.request;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;
    public Byte getCommand() {
        return Command.CREATEGROUP_REQUEST;
    }
}
