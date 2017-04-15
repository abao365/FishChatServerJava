package org.miaohong.fishchat.router.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.miaohong.fishchat.libnet.protocol.CmdSimple;
import org.miaohong.fishchat.router.config.RouterConfig;

import java.io.UnsupportedEncodingException;

/**
 * Created by haroldmiao on 2015/6/13.
 */
public class RouterServerHandler  extends ChannelHandlerAdapter {
    private ProtocolProc pp;
    public RouterServerHandler(RouterConfig rc) {
        pp = new ProtocolProc(rc);
    }

    public void parseCmd(ChannelHandlerContext ctx, CmdSimple cmd) {
        if (cmd == null) {
            return;
        }
        switch (cmd.getCmdName()) {


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
