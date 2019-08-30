package zxl.netty.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import zxl.netty.packet.request.CreateGroupRequestPacket;
import zxl.netty.packet.response.CreateGroupResponsePacket;
import zxl.netty.utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        //接收到创建群聊请求
        List<String> userIdList=createGroupRequestPacket.getUserIdList();

        //创建channel数组
        ChannelGroup channelGroup=new DefaultChannelGroup(ctx.executor());
        List<String> userNameList=new ArrayList<String>();

        //筛选出加入群聊的用户的channel 和 username
        for(String userId:userIdList){
            Channel channel= SessionUtil.getChannel(userId);
            if(channel!=null){
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        //创建响应请求
        CreateGroupResponsePacket createGroupResponsePacket=new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setUserIdList(userIdList);
        createGroupResponsePacket.setUserNameList(userNameList);
        createGroupResponsePacket.setGroupID(UUID.randomUUID().toString());

        SessionUtil.setChannelGroup(createGroupResponsePacket.getGroupID(),channelGroup);

        channelGroup.writeAndFlush(createGroupResponsePacket);
        System.out.println("群创建成功,id为【"+createGroupResponsePacket.getGroupID()+"】");
        System.out.println("群里面有："+createGroupResponsePacket.getUserNameList());
    }
}
