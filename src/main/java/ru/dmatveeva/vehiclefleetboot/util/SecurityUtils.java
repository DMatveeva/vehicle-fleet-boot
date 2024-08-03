package ru.dmatveeva.vehiclefleetboot.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {

    private SecurityUtils() {
    }

   public static String getUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        User user = (User) auth.getPrincipal();
        return user.getUsername();
    }

     /*

    public static AuthorizedManager get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static Manager getAuthManager() {
        return get().getManager();
    }*/

}