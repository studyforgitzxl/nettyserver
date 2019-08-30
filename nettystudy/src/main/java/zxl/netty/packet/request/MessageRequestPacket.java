package zxl.netty.packet.request;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

@Data
public class MessageRequestPacket extends Packet {
    private String message;//消息
    private String toUserId;

    public MessageRequestPacket(){}
    public MessageRequestPacket(String message,String toUserId){
        this.message=message;
        this.toUserId=toUserId;
    }
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
