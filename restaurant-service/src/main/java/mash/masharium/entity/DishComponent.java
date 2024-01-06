package mash.masharium.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "dish_component")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DishComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", referencedColumnName = "id", nullable = false)
    Dish dish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", referencedColumnName = "id", nullable = false)
    Component component;

    @Column(name = "quantity", nullable = false)
    BigDecimal quantity;

}
