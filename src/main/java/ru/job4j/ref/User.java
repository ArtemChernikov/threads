package ru.job4j.ref;

/**
 * @author Artem Chernikov
 * @version 1.0
 * @since 24.11.2022
 */
public class User {
    private int id;
    private String name;

    public static User of(String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
