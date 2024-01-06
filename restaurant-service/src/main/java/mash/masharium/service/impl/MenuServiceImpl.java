package mash.masharium.service.impl;


import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.request.MenuCreationRequest;
import mash.masharium.api.restaurant.response.MenuResponse;
import mash.masharium.entity.Menu;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.MenuMapper;
import mash.masharium.repository.MenuRepository;
import mash.masharium.service.DishComponentService;
import mash.masharium.service.DishService;
import mash.masharium.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final DishService dishService;

    private final DishComponentService dishComponentService;
    private final MenuMapper menuMapper;

    @Override
    public MenuResponse getMenu(UUID uuid) {
        Menu menu = menuRepository.findById(uuid)
                .orElseThrow(() -> new NotFountException(menuRepository.getErrorMassage()));
        return menuMapper.mapMenuToMenuResponseDto(menu,
                dishComponentService.computeDishesAvailability(menu.getDishList()));

    }

    @Override
    public UUID createMenu(MenuCreationRequest menu) {
        return menuRepository.save(menuMapper.mapMenuCreationRequestToMenu(menu)).getId();
    }

    @Override
    public MenuResponse addDishesToMenu(Set<UUID> dishIds, UUID menuId) {
        Menu menu = findMenu(menuId);
        menu.getDishList().addAll(dishService.getDishes(dishIds));
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.mapMenuToMenuResponseDto(savedMenu,
                dishComponentService.computeDishesAvailability(savedMenu.getDishList()));
    }

    @Override
    public MenuResponse removeDishesFromMenu(Set<UUID> dishIds, UUID menuId) {
        Menu menu = findMenu(menuId);
        dishIds.forEach(dishId -> menu.getDishList().removeAll(dishService.getDishes(dishIds)));
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.mapMenuToMenuResponseDto(savedMenu,
                dishComponentService.computeDishesAvailability(savedMenu.getDishList()));
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFountException(menuRepository.getErrorMassage()));
    }
}
