package ingameModule;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySource("classpath:ingameSettings.properties")
public class IngameModuleConfiguration {

    @Resource
    Environment environment;

    @Bean
    public IngameModule ingameModule() { return new IngameModule(); }

    @Bean
    public IngameSettings ingameSettings() {
        return new IngameSettings(Integer.parseInt(environment.getProperty("player.speed")));
    }
}
