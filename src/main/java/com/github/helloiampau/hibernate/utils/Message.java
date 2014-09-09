package com.github.helloiampau.hibernate.utils;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */
public class Message {

  private HttpServletResponse response;
  private Object content;

  public Message(HttpServletResponse response) {
    this.response = response;
    this.response.setContentType("application/json");

  }

  public void setContent(Object data) {
    this.content = data;

  }

  public void writeData() throws IOException {
    PrintWriter writer = response.getWriter();
    writer.println(this);

  }

  public void writeData(Object data) throws IOException {
    this.setContent(data);
    this.writeData();

  }

  @Override
  public String toString() {
    Gson gson = new Gson();

    return gson.toJson(this.content);
  }

}
