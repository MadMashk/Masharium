package mash.masharium.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "bonus_accounts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BonusAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "user_id")
    UUID userId;

    Integer balance;

    @OneToMany(mappedBy = "accountId", targetEntity = BonusOperation.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<BonusOperation> operations;
}
