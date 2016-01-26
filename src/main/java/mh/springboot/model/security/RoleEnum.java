package mh.springboot.model.security;

public enum  RoleEnum {
    USER(1, "ROLE_USER"),
    ADMIN(2, "ROLE_ADMIN");

    private final long id;
    private final String roleName;

    private RoleEnum(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
