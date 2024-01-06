package mash.masharium.mapper;

import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.entity.Component;
import mash.masharium.entity.DishComponent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface ComponentMapper {

    List<ComponentDto> componentsToComponentDtos(List<Component> component);

    List<Component> mapComponentCreationRequestsToComponents(List<ComponentCreationRequest> componentCreationRequests);

    @Named("dishComponentsToComponentDtos")
    default List<ComponentDto> dishComponentsToComponentDtos(List<DishComponent> dishComponents) {
        if (Objects.isNull(dishComponents)) {
            return Collections.emptyList();
        }
        return dishComponents.stream()
                .map(this::dishComponentToComponentDto).toList();
    }

    @Named("dishComponentToComponentDto")
    @Mapping(target = "id", source = "component.id")
    @Mapping(target = "measureType", source = "component.measureType")
    @Mapping(target = "price", source = "component.price")
    @Mapping(target = "name", source = "component.name")
    ComponentDto dishComponentToComponentDto(DishComponent dishComponent);
}
