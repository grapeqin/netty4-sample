package com.grapeqin.netty.v6;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * 使用MessagePack来编解码的示例程序client端
 *
 * @description
 * @author qinzy
 * @date 2020-06-09
 */
public class EchoClient {

  public EchoClient(int seq) {
    this.seq = seq;
  }

  private int seq;

  public void connect(String host, int port) {

    NioEventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      Bootstrap bootstrap = new Bootstrap();
      bootstrap
          .group(workGroup)
          .channel(NioSocketChannel.class)
          .handler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline()
                      .addLast(new MessagePackDecoder())
                      .addLast(new MessagePackEncoder())
                      .addLast(new EchoClientHandler(seq));
                }
              });
      ChannelFuture f = bootstrap.connect(new InetSocketAddress(host, port)).sync();
      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) {
    String host = "127.0.0.1";
    int port = 8080;
    if (args != null && args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    new EchoClient(100).connect(host, port);
  }
}
