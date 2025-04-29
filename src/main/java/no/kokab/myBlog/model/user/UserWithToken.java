package no.kokab.myBlog.model.user;

import no.kokab.myBlog.util.JwtUtil;

public record UserWithToken(
    Long userId,
    String name,
    String email,
    String role,
    String token
) {
    public static UserWithToken fromUserEntity(UserEntity user) {
        String token = JwtUtil.generateToken(user);
        return new UserWithToken(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            token
        );
    }
}
