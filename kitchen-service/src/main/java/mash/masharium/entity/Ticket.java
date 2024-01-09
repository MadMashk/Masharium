package mash.masharium.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.dto.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tickets")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "order_id")
    UUID orderId;

    @Column(name = "user_executor_id")
    UUID userExecutorId;

    @Enumerated(EnumType.STRING)
    TicketStatus ticketStatus;

    @Column(name = "begin_time")
    LocalDateTime beginTime;

    @Column(name = "end_time")
    LocalDateTime endTime;


    @OneToMany(mappedBy = "ticketId", targetEntity = TicketDish.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<TicketDish> positions;
}
