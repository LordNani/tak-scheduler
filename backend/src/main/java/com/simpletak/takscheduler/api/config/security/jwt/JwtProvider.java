package com.simpletak.takscheduler.api.config.security.jwt;

import java.util.UUID;

public interface JwtProvider {
    String generateToken(String login, String role, UUID id);

    boolean validateToken(String token);

    String getLoginFromToken(String token);

    UUID getUserIdFromToken(String token);
}
