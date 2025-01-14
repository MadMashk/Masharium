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
import mash.masharium.api.restaurant.constant.ComponentQuantityChangingOperationType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "component_quantity_changing_operation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComponentQuantityChangingOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "order_id")
    UUID orderId;

    @OneToMany(mappedBy = "writingOffOperation", targetEntity = ComponentQuantityChangingOperationComponent.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<ComponentQuantityChangingOperationComponent> componentWritingOffOperationList;

    @Column(name = "operation_date", nullable = false)
    Instant date;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    ComponentQuantityChangingOperationType changingOperationType;


}
