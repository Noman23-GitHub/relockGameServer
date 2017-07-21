package gameObjectsModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameObjectsConfiguration {
    @Bean
    public GameObjectsModule gameObjectsModule() {
        return new GameObjectsModule();
    }
}
