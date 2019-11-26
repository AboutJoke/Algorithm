package com.sylvan.algorithm;

import com.ycbjie.webviewlib.X5WebUtils;

/**
 * @ClassName: com.sylvan.algorithm
 * @Author: sylvan
 * @Date: 19-11-26
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        X5WebUtils.init(this);
    }
}
