package mh.springboot.model;

public enum  RoleEnum {
    USER(1),
    ADMIN(2);

    private long id;

    private RoleEnum(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
