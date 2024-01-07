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
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Table(name = "quantity_changing_operation_components")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComponentQuantityChangingOperationComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", referencedColumnName = "id", nullable = false)
    Component component;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_quantity_changing_operation_id", referencedColumnName = "id", nullable = false)
    ComponentQuantityChangingOperation writingOffOperation;

    @Column(name = "quantity", nullable = false)
    BigDecimal quantity;

    @Column(name = "quantity_before", nullable = false)
    BigDecimal quantityBefore;

    @Column(name = "quantity_after", nullable = false)
    BigDecimal quantityAfter;
}
