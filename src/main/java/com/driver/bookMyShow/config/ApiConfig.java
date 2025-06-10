package com.driver.bookMyShow.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api")
public class ApiConfig {
    private String baseurlfordata;
    private String baseurlforposter;
    private String apikey;
}
