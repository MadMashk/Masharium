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

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "table_client")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TableClient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_reservation_id", referencedColumnName = "id", nullable = false)
    TableReservation tableReservation;

}
