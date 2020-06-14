package com.grapeqin.netty.v6;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import org.msgpack.MessagePack;

/**
 * @description
 * @author qinzy
 * @date 2020-06-14
 */
public class MessagePackDecoder extends ByteToMessageDecoder {
  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    int length = in.readableBytes();
    byte[] buffer = new byte[length];
    in.readBytes(buffer);
    MessagePack messagePack = new MessagePack();
    out.add(messagePack.read(buffer));
  }
}
