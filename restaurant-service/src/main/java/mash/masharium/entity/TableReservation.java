package mash.masharium.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "table_reservation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_table_id", referencedColumnName = "id", nullable = false)
    RestaurantTable restaurantTable;

    @OneToMany(mappedBy = "tableReservation", targetEntity = TableClient.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<TableClient> tableClients;

    @Column(name = "reservation_date", nullable = false)
    LocalDateTime reservationDate;

    LocalDateTime reservationEnd;

    String name;
}
