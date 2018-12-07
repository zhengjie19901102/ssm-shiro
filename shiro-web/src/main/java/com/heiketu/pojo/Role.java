package com.heiketu.pojo;

import javax.persistence.Table;

@Table(name="user_roles")
public class Role {
    private String role_name;
    private String username;

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
