package com.reactifyx.configbean.configuration;

import com.reactifyx.Autowired;
import com.reactifyx.Bean;
import com.reactifyx.Configuration;
import com.reactifyx.configbean.entity.DB;
import com.reactifyx.configbean.entity.App;

@Configuration
public class DbConfiguration {
    @Autowired
    App app;

    @Bean
    public DB getDB() {
        return new DB(app);
    }
}
