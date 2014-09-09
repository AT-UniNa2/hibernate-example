package com.github.helloiampau.hibernate.model;

import javax.persistence.*;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @Column(nullable=false)
  private String name;

  @Column(nullable=false)
  private int price;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
