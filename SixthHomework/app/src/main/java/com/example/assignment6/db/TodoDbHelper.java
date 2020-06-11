package com.example.assignment6.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    // TODO 定义数据库名、版本；创建数据库
    private static final String SQL_CREATE_NOTES =
            "CREATE TABLE " + TodoContract.TABLE_NAME + " (" +
                    TodoContract.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    TodoContract.COLUMN_NAME_DATE + " INTEGER," +
                    TodoContract.COLUMN_NAME_STATE + " INTEGER," +
                    TodoContract.COLUMN_NAME_CONTENT + " TEXT," +
                    TodoContract.COLUMN_NAME_PRIORITY + " INTEGER)";

    private static final String SQL_DROP_NOTES =
            "DROP TABLE " + TodoContract.TABLE_NAME;

    public TodoDbHelper(Context context) {
        super(context, "todo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
