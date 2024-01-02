package mash.masharium.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ParameterResponseDto {

    private UUID id;

    private String name;

    private String value;

    private String description;

}
