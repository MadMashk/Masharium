package mash.masharium.service;

import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.request.DishCreationRequest;
import mash.masharium.entity.Dish;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DishService {
    Dish getDish(UUID dishId);

    List<Dish> getDishes(Set<UUID> uuids);

    List<DishDto> createDishes(List<DishCreationRequest> dish);

    List<Dish> getAll();
}
