package mash.masharium.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.service.ComponentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/components")
@Tag(name = "Ингридиенты")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMINISTRATOR')")
    public List<ComponentDto> createComponents(@RequestBody List<ComponentCreationRequest> componentCreationRequests) {
        return componentService.createComponents(componentCreationRequests);
    }


}
