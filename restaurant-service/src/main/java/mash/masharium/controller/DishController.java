package mash.masharium.controller;


import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.request.DishComponentsCreationRequest;
import mash.masharium.api.restaurant.request.DishCreationRequest;
import mash.masharium.api.restaurant.request.DishesComponentsWritingOffRequest;
import mash.masharium.service.DishComponentService;
import mash.masharium.service.DishService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;
    private final DishComponentService dishComponentService;

    @PostMapping
    List<DishDto> createDishes(@RequestBody List<DishCreationRequest> dishCreationRequests) {
        return dishService.createDishes(dishCreationRequests);
    }

    @PostMapping("/{id}/components")
    DishDto addComponentsToDish(@RequestBody List<DishComponentsCreationRequest> dishComponentsCreationRequests, @PathVariable(name = "id") UUID dishId) {
        return dishComponentService.addComponentsToDish(dishComponentsCreationRequests, dishId);
    }

    @PostMapping("/components/writing-off")
    public List<ComponentDto> writeOffComponentsOfDishes(@RequestBody DishesComponentsWritingOffRequest dishesComponentsWritingOffRequest){
        return dishComponentService.writeOffDishesComponents(dishesComponentsWritingOffRequest);
    }
}
