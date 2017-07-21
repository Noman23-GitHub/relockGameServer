package exceptionModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionModuleConfiguration {

    @Bean
    public ExceptionModule exceptionModule() {
        return new ExceptionModule();
    }
}
