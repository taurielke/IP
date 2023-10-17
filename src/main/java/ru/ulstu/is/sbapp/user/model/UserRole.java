package ru.ulstu.is.sbapp.user.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    TEACHER,
    DEAN,
    NOOB;

    //вложенный статичный класс, чтобы не использовать только константы в спринг секьюрити
    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + this.name();
    }

    public static final class AsString {
        public static final String ADMIN = PREFIX + "ADMIN";
        public static final String TEACHER = PREFIX + "TEACHER";
        public static final String DEAN = PREFIX + "DEAN";
        public static final String NOOB = PREFIX + "NOOB";
    }
}
