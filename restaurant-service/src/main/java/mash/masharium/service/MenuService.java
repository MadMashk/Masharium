package mash.masharium.service;

import mash.masharium.api.restaurant.request.MenuCreationRequest;
import mash.masharium.api.restaurant.response.MenuResponse;

import java.util.Set;
import java.util.UUID;

public interface MenuService {
    MenuResponse getMenu(UUID uuid);

    UUID createMenu(MenuCreationRequest menu);

    MenuResponse addDishesToMenu(Set<UUID> dishIds, UUID menuId);

    MenuResponse removeDishesFromMenu(Set<UUID> dishIds, UUID menuId);
}
