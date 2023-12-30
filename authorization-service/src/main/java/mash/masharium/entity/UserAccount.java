package mash.masharium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USER_ACCOUNT")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "first_name", nullable = false)
    String firstName;

    @Column(name = "last_name", nullable = false)
    String lastName;

    @Column(name = "date_of_birth", nullable = false)
    Instant dateOfBirth;

    @Column(name = "email")
    String email;

    @Column(name = "phone_number")
    Long phoneNumber;


}
