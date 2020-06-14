package com.grapeqin.netty.v6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.ObjectUtil;

/**
 * @description
 * @author qinzy
 * @date 2020-06-09
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

  private int seq;

  private int counter;

  public EchoClientHandler(int seq) {
    this.seq = seq;
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ObjectUtil.checkPositive(this.seq, "seq non positive");
    for (int i = 0; i < this.seq; i++) {
      UserInfo user = new UserInfo(i, "name" + i);
      ctx.write(user);
    }
    ctx.flush();
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    System.out.println(String.format("this is client receive %d message = %s", ++counter, msg));
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    System.err.println(String.format("ctx.channel = %s has exp : %s", ctx.channel(), cause));
    ctx.close();
  }
}
