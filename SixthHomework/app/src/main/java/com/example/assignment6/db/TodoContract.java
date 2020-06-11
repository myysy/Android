package com.example.assignment6.db;

/**
 * Created on 2019/1/22.
 *
 * @author xuyingyi@bytedance.com (Yingyi Xu)
 */
public final class TodoContract {

    // TODO 定义表结构和 SQL 语句常量
    public static final String TABLE_NAME = "note";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_STATE = "state";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static final String COLUMN_NAME_PRIORITY = "priority";

    private TodoContract() {
    }

}
