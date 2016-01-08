package com.kuvshinov.graph.core.auth;

/**
 * @author Sergey Kuvshinov
 */
public class User {

    private String name;

    private String password;

    public User() {
        this(null, null);
    }

    public User(String name) {
        this(name, null);
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof User))
            return false;

        User user = (User) obj;
        if (user.getName() == null)
            return false;

        return user.getName().equals(this.name);
    }
}
