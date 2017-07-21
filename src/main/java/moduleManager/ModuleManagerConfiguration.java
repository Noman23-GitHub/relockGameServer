package moduleManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ModuleManagerConfiguration {
    @Bean
    public ModuleManager moduleManager() {
        return new ModuleManager();
    }
}
