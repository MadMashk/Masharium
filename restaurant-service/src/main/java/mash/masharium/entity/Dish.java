package mash.masharium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.restaurant.constant.DishType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dish")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", nullable = false)
    String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "dish_type", nullable = false)
    DishType dishType;

    @Column(name = "price", nullable = false)
    BigDecimal price;

    @OneToMany(mappedBy = "dish", targetEntity = DishComponent.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<DishComponent> dishComponents;


}
