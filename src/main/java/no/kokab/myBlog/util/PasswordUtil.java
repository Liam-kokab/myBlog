package no.kokab.myBlog.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password, String email) {
        String salt = email.toLowerCase();  // Normalize email to ensure uniqueness
        String saltedPassword = password + salt;
        return bCryptPasswordEncoder.encode(saltedPassword);
    }

    public static boolean verifyPassword(String rawPassword, String email, String hashedPassword) {
        String saltedPassword = rawPassword + email.toLowerCase();
        return bCryptPasswordEncoder.matches(saltedPassword, hashedPassword);
    }
}