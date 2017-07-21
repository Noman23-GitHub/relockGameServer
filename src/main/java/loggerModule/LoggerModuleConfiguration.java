package loggerModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerModuleConfiguration {
    @Bean
    public LoggerModule loggerModule() { return new LoggerModule(); }
}
