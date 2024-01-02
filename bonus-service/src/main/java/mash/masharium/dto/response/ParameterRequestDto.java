package mash.masharium.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ParameterRequestDto {

    private UUID id;

    private String name;

    private String value;

    private String description;
}
