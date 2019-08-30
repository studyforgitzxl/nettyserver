package zxl.netty.packet.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import zxl.netty.command.Command;
import zxl.netty.packet.Packet;

@Data
public class LoginResponsePacket extends Packet {
    @JSONField
    private boolean success;

    private String userId;
    @JSONField
    private String reason;

    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
