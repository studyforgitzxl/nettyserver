package zxl.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import zxl.netty.packet.Packet;
import zxl.netty.packet.codec.PacketCodec;

import java.util.List;

public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketCodecHandler INSTANCE=new PacketCodecHandler();
    private PacketCodecHandler(){}

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        ByteBuf byteBuf=ctx.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf,packet);
        out.add(byteBuf);
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
