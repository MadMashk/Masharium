package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.common.DishAvailability;
import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.constant.DishStatus;
import mash.masharium.api.restaurant.request.DishComponentsCreationRequest;
import mash.masharium.api.restaurant.request.DishesComponentsQuantityChangingRequest;
import mash.masharium.entity.Component;
import mash.masharium.entity.Dish;
import mash.masharium.entity.DishComponent;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.DishComponentMapper;
import mash.masharium.mapper.DishMapper;
import mash.masharium.repository.DishComponentRepository;
import mash.masharium.repository.DishRepository;
import mash.masharium.service.ComponentService;
import mash.masharium.service.DishComponentService;
import mash.masharium.service.DishService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishComponentServiceImpl implements DishComponentService {

    private final DishComponentRepository dishComponentRepository;
    private final ComponentService componentService;
    private final DishComponentMapper dishComponentMapper;
    private final DishMapper dishMapper;
    private final DishService dishService;

    @Override
    public DishDto addComponentsToDish(List<DishComponentsCreationRequest> dishComponentsCreationRequests, UUID dishId) {
        Dish dish = dishService.getDish(dishId);

        List<DishComponent> componentQuantityPairList = dishComponentsCreationRequests.stream()
                .map(dishComponentsCreationRequest -> {
                    Component component = componentService.findById(dishComponentsCreationRequest.getComponentId());
                    BigDecimal quantity = dishComponentsCreationRequest.getQuantity();
                    return new Pair<>(quantity, component);
                })
                .map(pair -> dishComponentMapper.mapComponentAndDishAndQuantityToDishComponent(pair.b, dish, pair.a))
                .toList();

        dishComponentRepository.saveAll(componentQuantityPairList);

        return dishMapper.mapDishComponentsOfOneDishToDishDto(componentQuantityPairList);

    }

    @Override
    public List<DishAvailability> computeDishesAvailability(List<Dish> dishes) {
        return dishes.stream().map(this::createDishAvailability).toList();
    }

    @Override
    public List<ComponentDto> accrualDishesComponents(DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest) {
        return componentsDishesQuantityChangingPreparation(this.componentService::accrualComponents, dishesComponentsQuantityChangingRequest);
    }

    @Override
    public List<ComponentDto> writeOffDishesComponents(DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest) {
        return componentsDishesQuantityChangingPreparation(this.componentService::writeOffComponents, dishesComponentsQuantityChangingRequest);
    }


    private List<ComponentDto> componentsDishesQuantityChangingPreparation(BiFunction<Map<Component, BigDecimal>, UUID, List<ComponentDto>> quantityChangingFunction,
                                                                           DishesComponentsQuantityChangingRequest dishesComponentsQuantityChangingRequest) {
        List<Dish> dishes = dishService.getDishes(dishesComponentsQuantityChangingRequest.getDishesQuantityMap().keySet());

        if (!Objects.equals(dishes.size(), dishesComponentsQuantityChangingRequest.getDishesQuantityMap().entrySet().size())) {
            throw new NotFountException(DishRepository.ERROR_MESSAGE);
        }
        return dishes.stream().flatMap(dish ->
                quantityChangingFunction.apply(dish.getDishComponents().stream()
                                        .collect(Collectors.toMap(DishComponent::getComponent,
                                                dishComponent -> dishComponent.getQuantity()
                                                        .multiply(BigDecimal.valueOf(dishesComponentsQuantityChangingRequest.getDishesQuantityMap()
                                                                .get(dish.getId()))))),
                                dishesComponentsQuantityChangingRequest.getOrderId())
                        .stream()
        ).toList();
    }

    private Boolean checkComponentAvailability(DishComponent dishComponent) {
        if (dishComponent.getComponent().getQuantity().compareTo(dishComponent.getQuantity()) < 1) {
            return Boolean.FALSE;
        } else {
            return true;
        }
    }

    private DishAvailability createDishAvailability(Dish dish) {
        long unavailableComponentsCount = dish.getDishComponents().stream()
                .map(this::checkComponentAvailability)
                .filter(answer -> Objects.equals(Boolean.FALSE, answer))
                .count();

        if (unavailableComponentsCount > 0) {
            return dishMapper.mapToDishAvailability(dish, DishStatus.UNAVAILABLE);
        } else {
            return dishMapper.mapToDishAvailability(dish, DishStatus.AVAILABLE);
        }
    }


}
