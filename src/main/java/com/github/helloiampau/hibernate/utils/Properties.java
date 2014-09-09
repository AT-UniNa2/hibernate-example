package com.github.helloiampau.hibernate.utils;

import com.github.helloiampau.hibernate.model.Item;
import com.github.helloiampau.hibernate.model.Profile;
import com.github.helloiampau.hibernate.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 05 September 2014.
 */
public class Properties {

  private static SessionFactory dbSessionFactory;

  public static String DB_USERNAME = "DB_USERNAME";
  public static String DB_PASSWORD = "DB_PASSWORD";
  public static String DB_URL = "DB_URL";
  public static String TEST_ENV = "TEST_ENV";

  public static String get(String variable) {
    return System.getenv(variable);
  }

  public static void closeSessionFactory() {
    if(Properties.dbSessionFactory == null)
      return;

    Properties.dbSessionFactory.close();
    Properties.dbSessionFactory = null;
  }

  public static void openSessionFactory() {
    if(Properties.dbSessionFactory != null)
      return;

    AnnotationConfiguration config = new AnnotationConfiguration()
            .setProperty("hibernate.connection.url", Properties.get(Properties.DB_URL))
            .setProperty("hibernate.connection.username", Properties.get(Properties.DB_USERNAME))
            .setProperty("hibernate.connection.password", Properties.get(Properties.DB_PASSWORD))
            .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
            .addPackage("com.github.helloiampau.hibernate.model")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Item.class)
            .addAnnotatedClass(Profile.class);

    if(Properties.get(Properties.TEST_ENV) != null)
      config.setProperty("hibernate.hbm2ddl.auto", "create");

    Properties.dbSessionFactory = config.buildSessionFactory();
  }

  public static Session getDbSession() {
    if(Properties.dbSessionFactory == null)
      Properties.openSessionFactory();

    return Properties.dbSessionFactory.openSession();
  }

}
