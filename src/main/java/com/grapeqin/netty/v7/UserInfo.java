package com.grapeqin.netty.v7;

import org.msgpack.annotation.Message;

/**
 * @description
 * @author qinzy
 * @date 2020-06-14
 */
@Message
public class UserInfo {
  private int id;
  private String name;

  public UserInfo(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public UserInfo() {}

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "UserInfo{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
