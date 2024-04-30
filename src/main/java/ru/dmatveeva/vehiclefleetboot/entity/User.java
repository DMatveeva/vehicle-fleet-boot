package ru.dmatveeva.vehiclefleetboot.entity;


import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractBaseEntity {
    public static final String BY_LOGIN = "Vehicle.getByLogin";

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @ManyToMany(mappedBy = "manager")
    @Fetch(FetchMode.JOIN)
    private List<Enterprise> enterprise;
}
