package com.grapeqin.netty.v7;

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
    System.out.println(
        String.format("this is server receive %d message = " + "%s", ++counter, msg));
    ctx.writeAndFlush(msg);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.err.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
