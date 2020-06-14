package com.grapeqin.netty.v3;

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

  private int counter;

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
  	String str = (String)msg;
    System.out.println("server receive : " + str);
    String rsp = str.equalsIgnoreCase("QUERY TIME ORDER") ?
			new Date().toString()+"\n" : "BAD REQ\n";
    ByteBuf data = Unpooled.copiedBuffer(rsp.getBytes());
    ctx.writeAndFlush(data);
    System.out.println("counter = " + counter++);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.out.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
