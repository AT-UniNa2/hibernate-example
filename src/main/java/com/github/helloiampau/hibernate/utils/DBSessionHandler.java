package com.github.helloiampau.hibernate.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 09 September 2014.
 */
public class DBSessionHandler implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    System.out.println("Called once the container is initialized!");
    Properties.openSessionFactory();
    System.out.println("DB session factory opened!");
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    System.out.println("Called once the container is destroyed!");
    Properties.closeSessionFactory();
    System.out.println("DB session factory closed!");
  }

}
