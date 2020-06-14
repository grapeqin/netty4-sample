package com.grapeqin.netty.v1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Date;

/**
 * @description
 * @author qinzy
 * @date 2020-06-08
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;
    byte[] bytes = new byte[buf.readableBytes()];
    buf.readBytes(bytes);
    String str = new String(bytes, "UTF-8");
    System.out.println("server receive : " + str);
    String rsp = str.equalsIgnoreCase("QUERY TIME ORDER") ? new Date().toString() : "BAD REQ";
    ByteBuf data = Unpooled.copiedBuffer(rsp.getBytes());
    ctx.writeAndFlush(data);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
