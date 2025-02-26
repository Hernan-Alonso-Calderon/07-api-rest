package com.restaurant.restaurant_management.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MENU")
public class Menu {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, length = 45)
  private String menuName;

  @Column(length = 200)
  private String description;

  private Boolean active;

  @OneToMany(targetEntity = Dish.class, mappedBy = "menu")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private List<Dish> dishes = new ArrayList<>();

  public Menu(Integer id, String menuName, String description, Boolean active) {
    this.id = id;
    this.menuName = menuName;
    this.description = description;
    this.active = active;
  }
}
