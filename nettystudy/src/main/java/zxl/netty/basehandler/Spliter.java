package zxl.netty.basehandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import zxl.netty.packet.codec.PacketCodec;

public class Spliter extends LengthFieldBasedFrameDecoder {

    public Spliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        //屏蔽费本协议的客户端
        if(in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBR){
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
