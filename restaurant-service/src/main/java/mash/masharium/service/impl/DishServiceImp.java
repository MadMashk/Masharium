package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.DishDto;
import mash.masharium.api.restaurant.request.DishCreationRequest;
import mash.masharium.entity.Dish;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.DishMapper;
import mash.masharium.repository.DishRepository;
import mash.masharium.service.DishService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DishServiceImp implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public Dish getDish(UUID dishId) {
        return dishRepository.findById(dishId)
                 .orElseThrow(()-> new NotFountException(dishRepository.getErrorMassage()));
    }

    @Override
    public List<Dish> getDishes(Set<UUID> uuids) {
        List<Dish> dishList = dishRepository.findAllByIdIn(uuids);
        if(!Objects.equals(dishList.size(), uuids.size())){
            throw new NotFountException(dishRepository.getErrorMassage());
        }
        return dishList;
    }

    @Override
    public List<DishDto> createDishes(List<DishCreationRequest> dishCreationRequests) {
        return dishMapper.dishesToDishDtos(dishRepository
                .saveAll(dishMapper.mapDishCreationRequestToDish(dishCreationRequests)));
    }
}
