package com.grapeqin.netty.v3;

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

  private byte[] bytes = "QUERY TIME ORDER\n".getBytes();

  private int counter;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    for (int i = 0; i < 100; i++) {
      ByteBuf data = Unpooled.copiedBuffer(bytes);
      ctx.writeAndFlush(data);
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    String data = (String) msg;
    System.out.println(String.format("counter = %d client receive %s", counter++, data));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
