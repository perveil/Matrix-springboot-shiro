package org.tustcs.shiro.pojo;

import lombok.Data;

@Data
public class JwtRes {
    private String jwt;

    private String uid;

    private String menus;

    public JwtRes() {
    }

    public JwtRes(String jwt, String uid, String menus) {
        this.jwt = jwt;
        this.uid = uid;
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "JwtRes{" + "jwt='" + jwt + '\'' + ", uid='" + uid + '\'' + ", menus='" + menus + '\'' + '}';
    }
}
