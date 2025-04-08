package com.reactifyx.configbean;

import com.reactifyx.Autowired;
import com.reactifyx.Component;
import com.reactifyx.configbean.entity.DB;
import com.reactifyx.configbean.entity.App;

@Component
public class ClientClass {
    @Autowired
    private DB db;

    @Autowired
    private App app;

    @Autowired
    AnotherClientClass anotherClientClass;

    public String run() {
        return app.getApp() + " | " + db.getDB();
    }

    public String runScan() {
        return anotherClientClass.scan();
    }
}
