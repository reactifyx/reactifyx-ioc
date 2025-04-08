package com.reactifyx.configbean;

import com.reactifyx.Autowired;
import com.reactifyx.Component;
import com.reactifyx.configbean.entity.DB;

@Component
public class AnotherClientClass {
    @Autowired
    DB db;

    public String scan() {
        return "Scanning for " + db.getDB();
    }
}
