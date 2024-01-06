package mash.masharium.controller;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.service.ComponentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/components")
public class ComponentController {

    private final ComponentService componentService;

    @PostMapping
    public List<ComponentDto> createComponents(@RequestBody List<ComponentCreationRequest> componentCreationRequests) {
        return componentService.createComponents(componentCreationRequests);
    }


}
