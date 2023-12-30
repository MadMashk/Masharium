package mash.masharium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import mash.masharium.api.auth.constant.UserRoleType;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "USER_ROLE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_login_data_id", referencedColumnName = "id", nullable = false)
    UserLoginData userLoginData;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    UserRoleType userRoleType;

}
