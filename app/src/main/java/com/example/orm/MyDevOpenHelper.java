package com.example.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.orm.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper {
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
