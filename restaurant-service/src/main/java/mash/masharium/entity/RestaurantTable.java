package mash.masharium.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "restaurant_table")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "capacity", nullable = false)
    Integer capacity;

    @OneToMany(mappedBy = "restaurantTable", targetEntity = TableReservation.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<TableReservation> tableReservations;

}
