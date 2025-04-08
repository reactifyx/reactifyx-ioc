package com.reactifyx.configbean.configuration;

import com.reactifyx.Bean;
import com.reactifyx.Configuration;
import com.reactifyx.configbean.entity.App;

@Configuration
public class AppConfiguration {
    @Bean
    public App getApp() {
        return new App();
    }
}
