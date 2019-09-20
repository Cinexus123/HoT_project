package rakietrak.networkmock.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import rakietrak.networkmock.config.properties.AuthProperties;
import rakietrak.networkmock.config.properties.EthDataProviderProperties;
import rakietrak.networkmock.config.properties.UnifiProperties;

@EnableScheduling
@Configuration
@EnableConfigurationProperties({
        UnifiProperties.class,
        EthDataProviderProperties.class,
        AuthProperties.class
})
public class Config {

}
