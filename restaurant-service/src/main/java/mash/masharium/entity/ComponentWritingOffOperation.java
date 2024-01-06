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
@Table(name = "writing_off_operation_components")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComponentWritingOffOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id", referencedColumnName = "id", nullable = false)
    Component component;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writing_off_operation_id", referencedColumnName = "id", nullable = false)
    WritingOffOperation writingOffOperation;

    @Column(name = "quantity", nullable = false)
    BigDecimal quantity;

    @Column(name = "quantity_before", nullable = false)
    BigDecimal quantityBefore;

    @Column(name = "quantity_after", nullable = false)
    BigDecimal quantityAfter;
}
