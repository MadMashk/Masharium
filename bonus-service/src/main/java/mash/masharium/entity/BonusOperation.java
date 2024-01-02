package mash.masharium.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.bonus.constant.OperationStatus;
import mash.masharium.api.bonus.constant.OperationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bonus_operations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BonusOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "account_id")
    UUID accountId;

    @Column(name = "order_id")
    UUID orderId;

    Integer summa;

    LocalDateTime time;

    @Enumerated(EnumType.STRING)
    OperationStatus status;

    @Enumerated(EnumType.STRING)
    OperationType type;

}
