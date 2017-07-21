package playerModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerModuleConfiguration {
    @Bean
    public PlayerModule playerModule() {
        return new PlayerModule();
    }
}
