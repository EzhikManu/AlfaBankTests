package config;

import org.aeonbits.owner.Config;
@Config.Sources({"classpath:properties/credentials.properties"})
public interface CredentialsConfig extends Config {
    String login();
    String password();
}
