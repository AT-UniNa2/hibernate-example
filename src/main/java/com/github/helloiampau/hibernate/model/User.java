package com.github.helloiampau.hibernate.model;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */

@Entity
public class User {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(nullable=false, unique=true)
  private String username;

  @Column(nullable=false)
  private String password;

  @OneToOne
  private Profile profile;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = User.encryptPassword(password);
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public static String encryptPassword(String plainPassword) {
    String passwordHash = plainPassword;

    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(plainPassword.getBytes());
      passwordHash = new String(md.digest());
    } catch (NoSuchAlgorithmException e) {
      System.out.println("Using plain password");
      e.printStackTrace();
    }

    return passwordHash;
  }

}
