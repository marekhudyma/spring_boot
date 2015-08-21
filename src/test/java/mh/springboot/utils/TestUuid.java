package mh.springboot.utils;

import java.util.UUID;

public abstract class TestUuid {

    public static UUID uuid(int id) {
        return UUID.fromString(String.format("00000000-0000-0000-0000-%012d", id));
    }

}

