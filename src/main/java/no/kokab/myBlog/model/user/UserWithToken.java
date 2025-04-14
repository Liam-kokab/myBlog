package no.kokab.myBlog.model.user;

import no.kokab.myBlog.util.JwtUtil;

import java.util.HashMap;
import java.util.Map;

public class UserWithToken {
    private String token;
    private String name;
    private String email;
    private RoleName role;
    private Long userId;

    public UserWithToken(UserEntity user) {
        this.token = JwtUtil.generateToken(user);
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = RoleName.valueOf(user.getRole());
        this.userId = user.getUserId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("name", name);
        map.put("email", email);
        map.put("role", role);
        map.put("userId", userId);
        return map;
    }
}
