package zxl.netty.packet.response;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

@Data
public class MessageResponsePacket extends Packet {
    private String message;
    private String formId;
    private String formUserName;
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
