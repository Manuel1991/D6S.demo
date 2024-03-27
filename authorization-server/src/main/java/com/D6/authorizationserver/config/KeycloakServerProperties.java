package com.D6.authorizationserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {

    private String contextPath = "/auth";
    private String realmImportFile = "baeldung-realm.json";
    private AdminUser adminUser = new AdminUser();

    @Getter
    @Setter
    public static class AdminUser {
        private String username = "admin";
        private String password = "admin";
    }
}
