package com.D6.authorizationserver.config;

import com.D6.authorizationserver.AuthorizationServerApplication;
import org.keycloak.Config;
import org.keycloak.common.Profile;
import org.keycloak.common.profile.PropertiesFileProfileConfigResolver;
import org.keycloak.common.profile.PropertiesProfileConfigResolver;
import org.keycloak.platform.PlatformProvider;
import org.keycloak.services.ServicesLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class SimplePlatformProvider implements PlatformProvider {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationServerApplication.class);
    Runnable shutdownHook;

    public SimplePlatformProvider() {
        Profile.configure(new PropertiesProfileConfigResolver(System.getProperties()), new PropertiesFileProfileConfigResolver());
    }

    @Override
    public String name() {
        return "oauth-authorization-server";
    }

    @Override
    public void onStartup(Runnable runnable) {
        runnable.run();
    }

    @Override
    public void onShutdown(Runnable runnable) {
        this.shutdownHook = runnable;
    }

    @Override
    public void exit(Throwable cause) {
        LOG.error(cause.getMessage());
        ServicesLogger.LOGGER.fatal(cause);
        exit();
    }

    private void exit() {
        new Thread() {
            @Override
            public void run() {
                System.exit(1);
            }
        }.start();
    }

    @Override
    public File getTmpDirectory() {
        return new File(System.getProperty("java.io.tmpdir"));
    }

    @Override
    public ClassLoader getScriptEngineClassLoader(Config.Scope scriptProviderConfig) {
        return null;
    }
}
