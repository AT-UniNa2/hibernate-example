package com.github.helloiampau.hibernate;

import com.github.helloiampau.hibernate.exception.BadRequestException;
import com.github.helloiampau.hibernate.model.Profile;
import com.github.helloiampau.hibernate.model.User;
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
public class ProfileResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    Long profileId = Long.valueOf(request.getParameter("p"));

    try {
      if (profileId == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();
      Profile currentProfile = (Profile) session.get(Profile.class, profileId);

      if(currentProfile == null)
        throw new BadRequestException("Profile not found!");

      session.close();

      reply.writeData(currentProfile);
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
  public void doDelete(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    Long userId = Long.valueOf(request.getParameter("u"));

    try {
      if (userId == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();
      User currentUser = (User) session.get(User.class, userId);

      if(currentUser == null)
        throw new BadRequestException("User not found!");

      Profile currentProfile = currentUser.getProfile();

      if(currentProfile == null)
        throw new BadRequestException("Profile not found!");

      currentUser.setProfile(null);

      session.beginTransaction();
      session.delete(currentProfile);
      session.update(currentUser);
      session.getTransaction().commit();

      session.close();

      reply.writeData(currentProfile);
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
  public void doPut(HttpServletRequest request, HttpServletResponse response) {
    Message reply = new Message(response);

    String name = request.getParameter("n");
    String surname = request.getParameter("s");
    Integer age = Integer.valueOf(request.getParameter("a"));
    Long profileId = Long.valueOf(request.getParameter("p"));

    try {
      if (name == null || surname == null || age == null || profileId == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();
      Profile currentProfile = (Profile) session.get(Profile.class, profileId);

      if(currentProfile == null)
        throw new BadRequestException("Profile not found!");

      currentProfile.setName(name);
      currentProfile.setSurname(surname);
      currentProfile.setAge(age);

      session.beginTransaction();
      session.update(currentProfile);
      session.getTransaction().commit();

      session.close();

      reply.writeData(currentProfile);
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

    String name = request.getParameter("n");
    String surname = request.getParameter("s");
    Integer age = Integer.valueOf(request.getParameter("a"));
    Long userId = Long.valueOf(request.getParameter("u"));

    try {
      if(name == null || surname == null || age == null || userId == null)
        throw new BadRequestException("Incomplete request!");

      Session session = Properties.getDbSession();
      User currentUser = (User)session.get(User.class, userId);

      if(currentUser == null)
        throw new BadRequestException("User not found!");
      if(currentUser.getProfile() != null)
        throw new BadRequestException("Profile already exists!");

      Profile newProfile = new Profile();
      newProfile.setName(name);
      newProfile.setSurname(surname);
      newProfile.setAge(age);

      currentUser.setProfile(newProfile);

      session.beginTransaction();

      session.save(newProfile);
      session.update(currentUser);

      session.getTransaction().commit();
      session.close();

      reply.writeData(newProfile);
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
