package com.grapeqin.netty.v7;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * @description
 * @author qinzy
 * @date 2020-06-14
 */
public class MessagePackEncoder extends MessageToByteEncoder<Object> {
  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
	  MessagePack messagePack = new MessagePack();
	  byte[] buffer = messagePack.write(msg);
	  out.writeBytes(buffer);
  }
}
