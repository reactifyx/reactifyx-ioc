package com.reactifyx.configbean.configuration;

import com.reactifyx.Autowired;
import com.reactifyx.Bean;
import com.reactifyx.Configuration;
import com.reactifyx.configbean.entity.App;
import com.reactifyx.configbean.entity.DB;

@Configuration
public class DbConfiguration {
    @Autowired
    App app;

    @Bean
    public DB getDB() {
        return new DB(app);
    }
}
