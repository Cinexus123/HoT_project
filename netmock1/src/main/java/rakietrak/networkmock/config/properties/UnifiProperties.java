package rakietrak.networkmock.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix="unifi")
public class UnifiProperties {
    String login;
    String password;
    String address;
    String tokenPath;
}
