package com.nobody.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class KeyValidator {
    private static final String KEY = "$2a$10$1Mce1SnVA/c44EOFHAWPE.BMdMtUZJ6KF4aLy/yNaMWqLJr4KX9iO";

    public static boolean isKeyValid(String key) {
        return new BCryptPasswordEncoder().matches(key, KEY);
    }
}
