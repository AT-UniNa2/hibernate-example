package com.github.helloiampau.hibernate;

import com.github.helloiampau.hibernate.exception.BadRequestException;
import com.github.helloiampau.hibernate.model.Item;
import com.github.helloiampau.hibernate.model.User;
import com.github.helloiampau.hibernate.utils.Message;
import com.github.helloiampau.hibernate.utils.Properties;
import org.hibernate.Session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */
public class PurchaseResource extends HttpServlet {

  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    Long userId = Long.valueOf(request.getParameter("u"));
    Long itemId = Long.valueOf(request.getParameter("i"));

    try {
      if(userId == null || itemId == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();

      User currentUser = (User) session.get(User.class, userId);

      if(currentUser == null)
        throw new BadRequestException("User not found");

      if(currentUser.getProfile() == null)
        throw new BadRequestException("You must create the profile first!");

      Item itemToBuy = (Item) session.get(Item.class, itemId);

      if(itemToBuy == null)
        throw new BadRequestException("Item not found!");

      Set<Item> items = currentUser.getProfile().getItems();
      items.add(itemToBuy);

      session.beginTransaction();
      session.update(currentUser);
      session.getTransaction().commit();

      session.close();

      reply.writeData(items);
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
