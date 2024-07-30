package ru.dmatveeva.vehiclefleetboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Table(name = "managers")
@Getter
@Setter
@NoArgsConstructor
public class Manager extends AbstractBaseEntity {
    public static final String BY_LOGIN = "Manager.getByLogin";

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "enterprises_managers",
            joinColumns = @JoinColumn(name = "manager_id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    @Fetch(FetchMode.JOIN)
    private List<Enterprise> enterprises;
}