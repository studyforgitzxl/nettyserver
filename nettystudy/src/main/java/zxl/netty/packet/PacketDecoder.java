package zxl.netty.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import zxl.netty.packet.codec.PacketCodec;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.decode(in));
    }
}
