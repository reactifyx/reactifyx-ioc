package com.reactifyx.multiple;

import com.reactifyx.Autowired;
import com.reactifyx.Component;
import com.reactifyx.Qualifier;

@Component
public class Browser {
    private final Engine engine;
    private final BrowserRenderer renderer;

    @Autowired
    public Browser(@Qualifier("v8Engine") Engine engine, BrowserRenderer renderer) {
        this.engine = engine;
        this.renderer = renderer;
    }

    public String run() {
        return renderer.render("This browser run on " + engine.getName());
    }
}
