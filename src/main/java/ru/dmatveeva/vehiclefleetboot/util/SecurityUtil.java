package ru.dmatveeva.vehiclefleetboot.util;

public class SecurityUtil {

    private SecurityUtil() {
    }

  /*  public static AuthorizedManager safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedManager) ? (AuthorizedManager) principal : null;
    }

    public static AuthorizedManager get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static Manager getAuthManager() {
        return get().getManager();
    }*/

}