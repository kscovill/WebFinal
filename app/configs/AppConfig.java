package configs;

/**
 * Basic AppConfig Class, tells this is a config files and to scan for beans in controllers and services.
 */
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "controllers", "services" })
public class AppConfig {
}