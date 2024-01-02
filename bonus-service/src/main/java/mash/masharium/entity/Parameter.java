package mash.masharium.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "parameters")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;

    String value;

    String description;
}
