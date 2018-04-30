package xyx.njtech.edu.cn.diligentnode.db.calendar;

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import xyx.njtech.edu.cn.diligentnode.bean.calendar.Note;
import xyx.njtech.edu.cn.diligentnode.db.MyOpenHelper;

public class DBDao {

    private MyOpenHelper dbOpenHelper;

    public DBDao(Context context) {
        dbOpenHelper = new MyOpenHelper(context);
    }


    /**
     * 添加记事
     */
    public void addNote(Note note) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.title);
        values.put("content", note.content);
        values.put("date", note.date);
        db.insert("note", "_id", values);
        db.close();
    }

    /**
     * 根据日期获得记事列表
     *
     * @param date 日期
     * @return
     */
    public ArrayList<Note> searchNoteByDate(String date) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.query("note", new String[]{"_id", "date", "title", "content"}, " date = ?", new String[]{date}, null, null, "_id asc");
        ArrayList<Note> list = new ArrayList<Note>();

        Log.e("date", "db noteDate = " + date);

        while (cursor.moveToNext()) {
            Log.e("date", "db new Note() = ");
            Note note = new Note();
            note.id = cursor.getInt(cursor.getColumnIndex("_id"));
            ;
            note.title = cursor.getString(cursor.getColumnIndex("title"));
            note.content = cursor.getString(cursor.getColumnIndex("content"));
            note.date = cursor.getString(cursor.getColumnIndex("date"));
            list.add(note);
        }
        db.close();
        return list;
    }

    /**
     * 根据id获得记事
     *
     * @return
     */
    public Note searchNoteById(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        Cursor cursor = db.query("note", new String[]{"_id", "title", "content", "date"}, " _id =?", new String[]{String.valueOf(id)}, null, null, "_id asc");
        Note note = null;

        while (cursor.moveToNext()) {
            note = new Note();
            note.id = cursor.getInt(cursor.getColumnIndex("_id"));
            ;
            note.title = cursor.getString(cursor.getColumnIndex("title"));
            note.content = cursor.getString(cursor.getColumnIndex("content"));
            note.date = cursor.getString(cursor.getColumnIndex("date"));
        }
        db.close();
        return note;
    }

    /**
     * 根据id删除某个记事
     *
     * @param id
     */
    public void deleteNoteByDate(int id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete("note", "_id=?", new String[]{String.valueOf(id)});
    }


    /**
     * 修改某条记事
     *
     * @param note
     */
    public void updateNote(Note note) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.title);
        values.put("content", note.content);
        values.put("date", note.date);
        db.update("note", values, "_id=?", new String[]{String.valueOf(note.id)});
    }
}
