package xyx.njtech.edu.cn.diligentnode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyx.njtech.edu.cn.diligentnode.utils.CommonUtil;

import java.util.Date;

/**
 * 描述：数据库帮助类
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "note.db";// 数据库文件名
    private final static int DB_VERSION = 2;// 数据库版本

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建分类表
        db.execSQL("create table db_group(g_id integer primary key, " +
                "g_name varchar, g_order integer, g_color varchar, g_encrypt integer," +
                "g_create_time datetime, g_update_time datetime )");
        //创建笔记表
        db.execSQL("create table db_note(n_id integer primary key autoincrement, n_title varchar, " +
                "n_content varchar, n_group_id integer, n_group_name varchar, n_type integer, " +
                "n_bg_color varchar, n_encrypt integer, n_create_time datetime," +
                "n_update_time datetime )");


        //日历记事
        db.execSQL("CREATE TABLE note(_id integer primary key autoincrement,date varchar(10),title varchar(10),content varchar(10))");

        db.execSQL("create table alarmlist(_id integer primary key autoincrement,title char(20),isAllday int(20)," +
                "isVibrate int(20),year int(20),month int(20),day int(20),startTimeHour int(20),startTimeMinute int(20),"+
                "endTimeHour int(20),endTimeMinute int(20),alarmTime char(20),alarmColor char(20),alarmTonePath char(20),local char(20),"+
                "description char(100),replay char(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("delete from db_group");
        db.execSQL("insert into db_group(g_id, g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                "values(1, ?,?,?,?,?,?)", new String[]{"默认笔记", "1", "#FFFFFF", "0", CommonUtil.date2string(new Date()),CommonUtil.date2string(new Date())});
        db.execSQL("insert into db_group(g_id, g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                "values(2,?,?,?,?,?,?)", new String[]{"便签", "1", "#FFFFFF", "0", CommonUtil.date2string(new Date()),CommonUtil.date2string(new Date())});
        db.execSQL("insert into db_group(g_id, g_name, g_order, g_color, g_encrypt, g_create_time, g_update_time) " +
                "values(3,?,?,?,?,?,?)", new String[]{"私密", "1", "#FFFFFF", "0", CommonUtil.date2string(new Date()),CommonUtil.date2string(new Date())});
    }
}
