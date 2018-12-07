package com.heiketu.pojo;

import javax.persistence.Table;

@Table(name="role_permissions")
public class Permissions {
    private String permissons;
    private String role_name;

    public String getPermissons() {
        return permissons;
    }

    public void setPermissons(String permissons) {
        this.permissons = permissons;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
