package org.tustcs.shiro.pojo.token;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JwtToken implements AuthenticationToken {

    private String appId;

    private String ipHost;

    private String deviceInfo;

    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    public JwtToken(String appId, String jwt) {
        this.appId = appId;
        this.jwt = jwt;
    }

    public JwtToken(String appId, String ipHost, String deviceInfo, String jwt) {
        this.appId = appId;
        this.ipHost = ipHost;
        this.deviceInfo = deviceInfo;
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.appId;
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }
}
