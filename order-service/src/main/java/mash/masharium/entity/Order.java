package mash.masharium.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.api.order.constant.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "client_id")
    UUID clientId;

    LocalDateTime lastModified;

    LocalDateTime creationTime;

    LocalDateTime endTime;

    String address;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Enumerated(EnumType.STRING)
    OrderType type;

    @OneToMany(mappedBy = "orderId", targetEntity = Position.class, orphanRemoval = true, fetch = FetchType.LAZY)
    Set<Position> positions;

    @Column(name = "full_price")
    BigDecimal fullPrice;

    @Column(name = "applied_bonuses")
    BigDecimal appliedBonuses;

    @Column(name = "total_price")
    BigDecimal totalPrice;

    @Column(name = "is_paid")
    Boolean isPaid;

    Boolean isAuth;

}
