package xyx.njtech.edu.cn.diligentnode.caledar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.bean.calendar.Note;
import xyx.njtech.edu.cn.diligentnode.db.calendar.DBServer;
import xyx.njtech.edu.cn.diligentnode.BaseActivity;


public class CaledarActivity extends BaseActivity {

	CalendarView calendarView = null; //日历控件

	Button btnNew 	= null;  //新建一条记录
	ListView noteListView 	= null;  //记事列表

	String noteDate = "";//所选日期 以 yyyy-MM-dd 的形式保存

	ArrayList<Note> noteDatas = new ArrayList<Note>();//记事列表数据
	Note currentNote = null; //当前的记事

	TextView txvTip = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setTitle("日程记事");

		initView();//初始化界面组件

	}

	/**
	 * 初始化界面组件
	 */
	private void initView() {

		txvTip = (TextView) findViewById(R.id.txv);

		btnNew = (Button) findViewById(R.id.btnNewNote);
		btnNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				newNote();//新建一条记录
			}
		});

		calendarView = (CalendarView) findViewById(R.id.calendarView);
		calendarView.setShowWeekNumber(false);

		//以下是点击日历中的某一日，会触发的事件
		calendarView.setOnDateChangeListener(new OnDateChangeListener() {
			public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
				// TODO Auto-generated method stub
				refreshNoteList();//刷新记事列表
			}
		});

		noteListView = (ListView) findViewById(R.id.lv);
		noteListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
				// TODO Auto-generated method stub
				showNote(noteDatas.get(position));//查看这条记录
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshNoteList();//刷新记事列表
	}

	/**
	 * 刷新记事列表
	 */
	private void refreshNoteList() {

		noteDate = getDateString(calendarView.getDate());//获得当前日历控件中的所选日期
		loadData();//获取所选日期的记事列表数据

		if (noteDatas!=null && noteDatas.size()>0) {//如果此日有记事，则显示记事列表
			ListAdapter adapter = new ListAdapter(getApplicationContext());
			noteListView.setAdapter(adapter);
			txvTip.setText(noteDate+" 记事列表：");
		}else {//如果此日无记事，则提示暂无记事
			txvTip.setText(noteDate+" 暂无记事");
		}
	}

	/**
	 * 获取所选日期的记事列表数据
	 */
	private void loadData() {
		noteDatas = DBServer.searchNoteByDate(getApplicationContext(), noteDate);
		//	Log.e("noteDatas", "noteDatas.size = "+noteDatas.size());
	}




	/**
	 * 新建一条记录
	 */
	private void newNote() {
		Intent intent = new Intent(CaledarActivity.this,EditCaledarNoteActivity.class);
		intent.putExtra("UItype", "newNote");//新建
		intent.putExtra("noteDate", noteDate);
		startActivity(intent);
	}

	/**
	 * 查看一条记录
	 */
	private void showNote(Note note) {

		Intent intent = new Intent(CaledarActivity.this,EditCaledarNoteActivity.class);
		intent.putExtra("UItype", "showNote");//查看
		intent.putExtra("note", note);
		startActivity(intent);
	}


	/**
	 * 获取当前日期， 返回形式为 yyyy-MM-dd
	 */
	private String getDateString(long date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate  =  new Date(date);
		return  formatter.format(curDate);
	}

	/**
	 * 记事列表数据适配
	 */
	class ListAdapter extends BaseAdapter {

		private Context mContext = null;
		private LayoutInflater mInflater = null;

		public ListAdapter(Context c) {
			mContext = c;
			mInflater = LayoutInflater.from(this.mContext);
		}

		@Override
		public int getCount() {
			return noteDatas.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ListViewHolder holder = null;
			if (convertView == null) {
				holder = new ListViewHolder();
				convertView = mInflater.inflate(R.layout.list_item_caledar, null);
				//初始化组件
				holder.txvName   = (TextView) convertView.findViewById(R.id.txvName);
				holder.btnOpen   = (Button) convertView.findViewById(R.id.btnOpen);
				convertView.setTag(holder);
			} else {
				holder=(ListViewHolder) convertView.getTag();
			}

			holder.txvName.setText(noteDatas.get(position).title);
			holder.btnOpen.setTag(noteDatas.get(position));
			holder.btnOpen.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showNote((Note)v.getTag());//查看这条记录
				}
			});

			return convertView;
		}

		class ListViewHolder {
			TextView txvName;
			Button btnOpen;
		}
	}
}

