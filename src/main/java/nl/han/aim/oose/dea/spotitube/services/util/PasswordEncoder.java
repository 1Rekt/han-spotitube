package nl.han.aim.oose.dea.spotitube.services.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
