package com.example.orm;

import android.app.Application;

import com.example.orm.model.DaoMaster;
import com.example.orm.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private static App app;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this,"notes-db");
        Database db = myDevOpenHelper.getWritableDb();
       mDaoSession= new DaoMaster(db).newSession();
       app = this;
    }

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
