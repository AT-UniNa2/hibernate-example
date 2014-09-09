package com.github.helloiampau.hibernate;

import com.github.helloiampau.hibernate.exception.BadRequestException;
import com.github.helloiampau.hibernate.model.Item;
import com.github.helloiampau.hibernate.utils.Message;
import com.github.helloiampau.hibernate.utils.Properties;
import org.hibernate.Session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */
public class ItemResource extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    String name = request.getParameter("n");
    Integer price = Integer.valueOf(request.getParameter("p"));

    try {
      if(name == null || price == null)
        throw new BadRequestException("Incomplete request!");

      Item newItem = new Item();
      newItem.setName(name);
      newItem.setPrice(price);

      Session session = Properties.getDbSession();
      session.beginTransaction();

      session.save(newItem);
      session.getTransaction().commit();

      session.close();


      reply.writeData(newItem);
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (BadRequestException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      e.printStackTrace();
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      e.printStackTrace();
    }
  }

}
