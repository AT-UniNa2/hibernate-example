package com.github.helloiampau.hibernate;

import com.github.helloiampau.hibernate.exception.BadRequestException;
import com.github.helloiampau.hibernate.model.User;
import com.github.helloiampau.hibernate.utils.Message;
import com.github.helloiampau.hibernate.utils.Properties;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */
public class UserResource  extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    String username = request.getParameter("un");
    String password = request.getParameter("p");

    try {
      if(username == null || password == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();
      Criteria searchCriteria = session.createCriteria(User.class);
      searchCriteria.add(Restrictions.eq("username", username));

      List result = searchCriteria.list();

      if(result.size() != 1)
        throw new BadRequestException("User not found!");

      User currentUser = (User) result.get(0);

      if(!User.encryptPassword(password).equals(currentUser.getPassword()))
        throw new BadRequestException("Wrong password!");

      reply.writeData(currentUser);
      response.setStatus(HttpServletResponse.SC_OK);

    } catch (BadRequestException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      e.printStackTrace();
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      e.printStackTrace();
    }

  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    String username = request.getParameter("un");
    String password = request.getParameter("p");

    try {
      if(username == null || password == null)
        throw new BadRequestException("Incomplete request!");

      User newUser = new User();
      newUser.setUsername(username);
      newUser.setPassword(password);

      Session session = Properties.getDbSession();
      session.beginTransaction();

      session.save(newUser);

      session.getTransaction().commit();
      session.close();

      reply.writeData(newUser);
      response.setStatus(HttpServletResponse.SC_OK);

    } catch(ConstraintViolationException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      e.printStackTrace();
    } catch(BadRequestException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      e.printStackTrace();
    } catch (IOException e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      e.printStackTrace();
    }


  }

}
