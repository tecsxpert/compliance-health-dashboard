package com.internship.tool.config;

import com.internship.tool.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setup() {

        jwtUtil = new JwtUtil();

        ReflectionTestUtils.setField(
                jwtUtil,
                "secret",
                "mySecretKeymySecretKeymySecretKey12345"
        );

        ReflectionTestUtils.setField(
                jwtUtil,
                "expiration",
                86400000L
        );
    }

    @Test
    void testGenerateToken() {

        String token = jwtUtil.generateToken("testuser");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testExtractUsername() {

        String token = jwtUtil.generateToken("testuser");

        String username = jwtUtil.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken() {

        String token = jwtUtil.generateToken("testuser");

        boolean valid = jwtUtil.validateToken(token);

        assertTrue(valid);
    }
}