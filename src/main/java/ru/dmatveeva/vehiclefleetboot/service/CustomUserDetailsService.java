package ru.dmatveeva.vehiclefleetboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var manager = managerRepository.findByLogin(username);

        if (manager == null) {
            throw new UsernameNotFoundException("Manager not found: " + username);
        }

       // PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
       /* List<String> roles = new ArrayList<>();
        roles.add(user.getRole());*/
        return User.withDefaultPasswordEncoder()
                .username(manager.getLogin())
                .password(manager.getPassword())
                .roles("USER")
                .build();

       /* return new AuthorizedManager(manager);
        return new User(manager.getLogin(), encoder.encode(manager.getPassword()),
                getAuthorities(List.of("ROLE_USER")));*/
    }

    private List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }


}
