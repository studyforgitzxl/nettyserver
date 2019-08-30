package zxl.netty.command;

public interface Command {
    Byte LOGIN_REQUEST = 1;//登录请求指令
    Byte LOGIN_RESPONSE = 2;//登录响应指令
    Byte MESSAGE_REQUEST =3;//消息请求指令
    Byte MESSAGE_RESPONSE=4;//消息响应指令
    Byte CREATEGROUP_REQUEST=5;
    Byte CREATEGROUP_RESPONSE=6;
    Byte GROUPMESSAGE_REQUEST=7;
}
