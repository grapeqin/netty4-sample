package com.grapeqin.netty.v1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description
 * @author qinzy
 * @date 2020-06-09
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

  private byte[] bytes = "QUERY TIME ORDER".getBytes();

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
  	ByteBuf data = Unpooled.copiedBuffer(bytes);
    ctx.writeAndFlush(data);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf data = (ByteBuf) msg;
    byte[] buffer = new byte[data.readableBytes()];
    data.readBytes(buffer);
    System.out.println(new String(buffer, "UTF-8"));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
