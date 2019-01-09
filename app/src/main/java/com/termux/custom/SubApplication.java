package com.termux.custom;

import android.app.Application;

/**
 * Created by LiZhe on 2019-01-09.
 * com.termux.custom
 */
public class SubApplication extends Application {

    private static SubApplication application;

    /**
     * Gets Application object
     *
     * @return SubApplication
     */
    public static synchronized SubApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
