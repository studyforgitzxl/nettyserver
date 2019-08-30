package zxl.netty.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import zxl.netty.packet.codec.PacketCodec;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE.encode(out,packet);
    }
}
