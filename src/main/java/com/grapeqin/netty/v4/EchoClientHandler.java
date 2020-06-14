package com.grapeqin.netty.v4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description
 * @author qinzy
 * @date 2020-06-09
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

  private byte[] bytes =
		  "Hello,this is grapeQin,Welcome to Netty.$_".getBytes();

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
    System.out.println(String.format("this is client receive %d message = %s", ++counter, data));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
