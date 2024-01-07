package mash.masharium.service;

import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.common.DishAvailability;
import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.request.DishComponentsCreationRequest;
import mash.masharium.api.restaurant.request.DishesComponentsQuantityChangingRequest;
import mash.masharium.entity.Dish;

import java.util.List;
import java.util.UUID;

public interface DishComponentService {
    DishDto addComponentsToDish(List<DishComponentsCreationRequest> dishComponentsCreationRequests, UUID dishId);

    List<DishAvailability> computeDishesAvailability(List<Dish> dishes);

    List<ComponentDto> writeOffDishesComponents(DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest);

    List<ComponentDto> accrualDishesComponents(DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest);
}
