package com.grapeqin.netty.v5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 使用固定长度解码器来解决TCP粘包/拆包问题的示例程序server端
 *
 * @description
 * @author qinzy
 * @date 2020-06-08
 */
public class TelnetServer {

  public void bind(int port) throws InterruptedException {
    NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();

      serverBootstrap
          .group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline()
                      .addLast(new FixedLengthFrameDecoder(10))
                      .addLast(new StringDecoder())
                      .addLast(new TelnetServerHandler());
                }
              });
      ChannelFuture f = serverBootstrap.bind(port).sync();

      f.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    int port = 8080;
    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    try {
      new TelnetServer().bind(port);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
