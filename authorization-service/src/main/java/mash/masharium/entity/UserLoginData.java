package mash.masharium.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "USER_LOGIN_DATA")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "login", nullable = false)
    String login;

    @OneToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    UserAccount userAccount;

    @Column(name = "password", nullable = false)
    String password;

    @OneToMany(mappedBy = "userLoginData", targetEntity = UserRole.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", targetEntity = UserDevice.class, orphanRemoval = true, fetch = FetchType.LAZY)
    List<UserDevice> userDevice;

    @Column(name = "is_logged", nullable = false)
    Boolean isLogged;

}
