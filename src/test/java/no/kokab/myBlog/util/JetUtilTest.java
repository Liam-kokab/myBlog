package no.kokab.myBlog.util;

import no.kokab.myBlog.model.user.RoleName;
import no.kokab.myBlog.model.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

class JwtUtilTest {
    @BeforeEach
    void setUp() {
        // Initialize the JwtUtil with a test secret key
        String testSecretKey = Base64.getEncoder().encodeToString("test-secret-key-this-needs-to-be-longer".getBytes());
        new JwtUtil().initWithCustomKey(testSecretKey);
    }

    @Test
    void testGenerateToken() {
        UserEntity user = new UserEntity(
            5L,
            "Test User",
            "test@test.com",
            "password",
            RoleName.USER
        );

        String token = JwtUtil.generateToken(user);
        assertNotNull(token);
        assertTrue(token.startsWith("ey"), "Token should start with 'ey'");
    }

}
