package mash.masharium.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "positions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Position {

    @Id
    UUID id;

    Integer quantity;

    UUID orderId;

    BigDecimal cost;
}
