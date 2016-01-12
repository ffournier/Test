package com.appstud.testappstud;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.appstud.testappstud.database.DaoMaster;
import com.appstud.testappstud.database.DaoSession;

public class MyApplication extends Application {

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext(), "dbappstud", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
