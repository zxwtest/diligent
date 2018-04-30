package xyx.njtech.edu.cn.diligentnode.db.calendar;

import java.util.ArrayList;

import android.content.Context;

import xyx.njtech.edu.cn.diligentnode.bean.calendar.Note;
import xyx.njtech.edu.cn.diligentnode.db.calendar.DBDao;


/**
 * 操作数据库数据的一些方法
 * @author leehy
 *
 */
public class DBServer {

	/**
	 * 添加记事
	 */
	public static void addNote(Context context,Note note){
		DBDao dbDao = new DBDao(context);
		dbDao.addNote(note);
	}

	/**
	 * 根据日期获得记事列表
	 * @param date 日期
	 * @return
	 */
	public static ArrayList<Note> searchNoteByDate(Context context,String date){
		DBDao dbDao = new DBDao(context);
		return dbDao.searchNoteByDate(date);
	}

	/**
	 * 根据id获得记事
	 * @param context
	 */
	public static Note searchNoteById(Context context,int id){
		DBDao dbDao = new DBDao(context);
		return dbDao.searchNoteById(id);
	}

	/**
	 * 修改某条记事
	 * @param context
	 * @param note
	 */
	public static void updateNote(Context context,Note note){
		DBDao dbDao = new DBDao(context);
		dbDao.updateNote(note);
	}


	/**
	 * 根据id删除某个记事
	 * @param id
	 */
	public static void deleteNoteByDate(Context context,int id){
		DBDao dbDao = new DBDao(context);
		dbDao.deleteNoteByDate(id);
	}
}
