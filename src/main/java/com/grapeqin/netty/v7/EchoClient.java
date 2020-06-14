package com.grapeqin.netty.v7;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import java.net.InetSocketAddress;

/**
 * 使用MessagePack来编解码/LengthField来处理TCP粘包/拆包的示例程序client端
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
                      .addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
                      .addLast(new MessagePackDecoder())
                      .addLast(new LengthFieldPrepender(2))
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
