package mash.masharium.integration.client;

import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.DishesComponentsQuantityChangingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "restaurant-client", url = "#{clientProperties.restaurantServiceUrl}")
public interface RestaurantServiceClient {

    @PostMapping("/dishes/components/writing-off")
    List<ComponentDto> writeOffComponentsOfDishes(@RequestBody DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest);

    @PostMapping("/dishes/components/accrual")
    List<ComponentDto> accrualComponentsOfDishes(@RequestBody DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest);
}
