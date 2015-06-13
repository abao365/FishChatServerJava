package org.miaohong.fishchat.msgserver.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.miaohong.fishchat.libnet.protocol.Cmd;
import org.miaohong.fishchat.libnet.protocol.CmdSimple;
import org.miaohong.fishchat.log.Log;
import org.miaohong.fishchat.msgserver.config.MsgServerConfig;

import java.io.UnsupportedEncodingException;

/**
 * Created by haroldmiao on 2015/6/13.
 */
public class MsgServerServerHandler  extends ChannelHandlerAdapter {
    private ProtocolProc pp;

    public MsgServerServerHandler(MsgServerConfig mc) {
        pp = new ProtocolProc(mc);
    }

    public void parseCmd(ChannelHandlerContext ctx, CmdSimple cmd) {
        Log.logger.info("parseCmd");
        if (cmd == null) {
            return;
        }

        Log.logger.info(cmd.getCmdName());

        switch (cmd.getCmdName()) {
            case Cmd.SEND_CLIENT_ID_CMD:
                pp.procSendClientID(ctx, cmd);

        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        CmdSimple cmd = new CmdSimple();
        String reqStr = null;
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        try {
            reqStr = new String(req, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.logger.info(reqStr);

        cmd = JSON.parseObject(reqStr, CmdSimple.class);
        parseCmd(ctx, cmd);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
