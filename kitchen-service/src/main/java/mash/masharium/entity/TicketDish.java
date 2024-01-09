package mash.masharium.entity;

import jakarta.persistence.*;
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
@Table(name = "ticket_dishes")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketDish {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "ticket_id")
    UUID ticketId;

    @Column(name = "dish_id")
    UUID dishId;

    Integer quantity;
}
