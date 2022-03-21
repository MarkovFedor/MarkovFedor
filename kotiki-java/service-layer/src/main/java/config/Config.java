package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.KotikiService;
import utils.CatDtoMapping;
import utils.OwnerDtoMapping;

@Configuration
public class Config {
    @Bean
    public KotikiService kotikiService() {
        return new KotikiService();
    }

    @Bean
    public CatDtoMapping catDtoMapping() {
        return new CatDtoMapping();
    }

    @Bean
    public OwnerDtoMapping ownerDTOMapping() {
        return new OwnerDtoMapping();
    }
}
