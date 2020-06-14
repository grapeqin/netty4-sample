package com.grapeqin.netty.v4;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description
 * @author qinzy
 * @date 2020-06-08
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

  private int counter;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    String str = (String) msg;
    System.out.println(
        String.format("this is server receive %d message = " + "%s", ++counter, str));
    ctx.writeAndFlush(Unpooled.copiedBuffer((str + "$_").getBytes()));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
