package ru.dmatveeva.vehiclefleetboot.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ManagerService/* implements UserDetailsService */{

 /*   private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Manager manager = managerRepository.findByLogin(login);
        if (manager == null) {
            throw new UsernameNotFoundException("Manager " + login + " is not found");
        }
        return new AuthorizedManager(manager);
    }*/
}
