package mash.masharium.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.MeasureType;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "component")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "quantity", nullable = false)
    BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "measure_type", nullable = false)
    MeasureType measureType;

    @Column(name = "price", nullable = false)
    BigDecimal price;

}
