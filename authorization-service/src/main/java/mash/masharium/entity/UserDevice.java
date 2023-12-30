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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "USER_DEVICE")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_device_id", referencedColumnName = "id", nullable = false)
    UserLoginData user;

    @Column(name = "enter_date", nullable = false)
    Instant enterDate;

    @Column(name = "device_info", nullable = false)
    String deviceInfo;

    @Column(name = "is_connected", nullable = false)
    Boolean isConnected;


}


