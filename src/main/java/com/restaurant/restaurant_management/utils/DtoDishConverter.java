package com.restaurant.restaurant_management.utils;

import com.restaurant.restaurant_management.dto.DishRequestDTO;
import com.restaurant.restaurant_management.dto.DishResponseDTO;
import com.restaurant.restaurant_management.models.Dish;
import com.restaurant.restaurant_management.models.Menu;

public class DtoDishConverter {

  public static Dish convertToDish(DishRequestDTO dishRequestDTO, Menu menu) {
    Dish dish = new Dish();
    dish.setDishName(dishRequestDTO.getDishName());
    dish.setDescription(dishRequestDTO.getDescription());
    dish.setBasePrice(dishRequestDTO.getBasePrice());
    dish.setIsPopular(dishRequestDTO.getIsPopular());
    dish.setMenu(menu);
    dish.setActive(dishRequestDTO.getActive());
    return dish;
  }

  public static DishResponseDTO convertToResponseDTO(Dish dish) {
    DishResponseDTO dishResponseDTO = new DishResponseDTO();
    dishResponseDTO.setId(dish.getId());
    dishResponseDTO.setDishName(dish.getDishName());
    dishResponseDTO.setDescription(dish.getDescription());
    dishResponseDTO.setBasePrice(dish.getBasePrice());
    dishResponseDTO.setIsPopular(dish.getIsPopular());
    dishResponseDTO.setActive(dish.getActive());
    return dishResponseDTO;
  }
}
