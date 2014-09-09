package com.github.helloiampau.hibernate.model;

import javax.persistence.*;
import java.util.Set;

/**
 * hibernate
 * Created by Pasquale Boemio <boemianrapsodi@gmail.com>
 * <p/>
 * 08 September 2014.
 */

@Entity
public class Profile {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;

  @Column(nullable=true)
  private String name;

  @Column(nullable=true)
  private String surname;

  @Column(nullable=true)
  private int age;

  // Lazy fetch type is used to load items list from db only when its getter is invoked.
  @ManyToMany(fetch=FetchType.LAZY)
  private Set<Item> items;
  // By using Set data type you define the join table fields as primary key.
  // Use List data type to enable the table with the duplication.

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }
}
