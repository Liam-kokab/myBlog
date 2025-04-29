package no.kokab.myBlog.model.user;

public record UserPublic(
    Long userId,
    String name
) {
    public static UserPublic fromUserEntity(UserEntity user) {
        return new UserPublic(
            user.getUserId(),
            user.getName()
        );
    }
}
