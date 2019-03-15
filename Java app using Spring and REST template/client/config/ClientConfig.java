package lab5.catalog.client.config;

import lab5.catalog.client.ui.ClientConsole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ClientConsole clientConsole() {
        return new ClientConsole();
    }
}
