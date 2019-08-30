package zxl.netty.packet.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import zxl.netty.command.Command;
import zxl.netty.command.CommandManager;
import zxl.netty.packet.request.LoginRequestPacket;
import zxl.netty.packet.Packet;
import zxl.netty.packet.response.LoginResponsePacket;
import zxl.netty.serializer.Serializer;
import zxl.netty.serializer.impl.JSONSerializer;

public class PacketCodec {
    //单例模式
    public static final PacketCodec INSTANCE = new PacketCodec();
    private PacketCodec(){}

    public static final int MAGIC_NUMBR = 0x12345678;

    public ByteBuf encode(ByteBuf byteBuf,Packet packet){
        //序列化JAVA对象
        byte[] bytes= Serializer.DEFAULT.serialize(packet);

        //实际编码过程
        byteBuf.writeInt(MAGIC_NUMBR);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        //跳过magicnumber
        byteBuf.skipBytes(4);

        //跳过版本
        byteBuf.skipBytes(1);
        //序列化算法标识

        byte serializerAlgorithm = byteBuf.readByte();

        //指令
        int command=byteBuf.readByte();

        //数据长度
        int length=byteBuf.readInt();

        //数据
        byte[] bytes=new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType=getRequestType(command);
        Serializer serializer=getSerializer(serializerAlgorithm);
        if(requestType!=null && serializer !=null){
            return serializer.deserialize(requestType,bytes);
        }

        return null;
    }

    private Class<? extends Packet> getRequestType(int command){
        Class<? extends Packet> requestType=CommandManager.commandMap.get((byte)command);
        return requestType;
    }

    private Serializer getSerializer(byte serializeAlgorithm){
        return new JSONSerializer();
    }
}
