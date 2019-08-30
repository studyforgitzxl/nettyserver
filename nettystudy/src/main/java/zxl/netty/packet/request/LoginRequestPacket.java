package zxl.netty.packet.request;

import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

}
