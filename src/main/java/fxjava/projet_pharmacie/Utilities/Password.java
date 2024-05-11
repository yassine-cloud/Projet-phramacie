package fxjava.projet_pharmacie.Utilities;

import org.mindrot.jbcrypt.BCrypt;

public class Password {



    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
