package mash.masharium.controller;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.request.MenuCreationRequest;
import mash.masharium.api.restaurant.response.MenuResponse;
import mash.masharium.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public UUID createMenu(@RequestBody MenuCreationRequest menuCreationRequest) {
        return menuService.createMenu(menuCreationRequest);
    }

    @GetMapping("/{id}")
    public MenuResponse getMenu(@PathVariable(name = "id") UUID menuId) {
        return menuService.getMenu(menuId);
    }

    @PostMapping("/{id}/dishes")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public MenuResponse addDishesToMenu(@RequestBody Set<UUID> dishesUuids, @PathVariable(name = "id") UUID menuId) {
        return menuService.addDishesToMenu(dishesUuids, menuId);
    }

    @DeleteMapping("/{id}/dishes")
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public MenuResponse deleteDishesFromMenu(@RequestBody Set<UUID> dishesUuids, @PathVariable(name = "id") UUID menuId) {
        return menuService.removeDishesFromMenu(dishesUuids, menuId);
    }
}
