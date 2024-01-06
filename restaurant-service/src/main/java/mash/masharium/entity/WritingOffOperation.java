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

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "writing_off_operation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WritingOffOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "order_id")
    UUID orderId;

    @OneToMany(mappedBy = "writingOffOperation", targetEntity = ComponentWritingOffOperation.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<ComponentWritingOffOperation> componentWritingOffOperationList;

    @Column(name = "operation_date", nullable = false)
    Instant date;

}
