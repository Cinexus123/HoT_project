package rakietrak.networkmock.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("eth")
@Data
public class EthDataProviderProperties {
    private String address;
}
