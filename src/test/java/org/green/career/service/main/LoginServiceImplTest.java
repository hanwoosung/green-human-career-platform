package org.green.career.service.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoginServiceImplTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testPasswordEncodingAndMatching() {
        String rawPassword = "p1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Encoded Password: " + encodedPassword);


        boolean isMatch = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println("Password matches: " + isMatch);

        assertTrue(isMatch);
    }

}
