package xyx.njtech.edu.cn.diligentnode.caledar;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.bean.calendar.Note;
import xyx.njtech.edu.cn.diligentnode.db.calendar.DBServer;
import xyx.njtech.edu.cn.diligentnode.BaseActivity;

/**
 * 编辑或查看日程记事的界面
 *
 */
public class EditCaledarNoteActivity extends BaseActivity {

	RelativeLayout etNoteLayout = null; //填写记事的布局
	EditText etNoteTitle  =  null;      //记事的标题
	EditText etNoteContent=  null;      //记事的内容

	LinearLayout txvNoteLayout = null; //填写记事的布局
	EditText txvNoteTitle  =  null;    //记事的标题
	EditText txvNoteContent=  null;    //记事的内容


	Button btnSave	 = null; //保存记录 按钮
	Button btnDelete = null; //删除记录 按钮
	Button btnEdit	 = null; //修改记录 按钮


	String noteTitle	=""; //标题
	String noteContent	=""; //内容
	String noteDate 	=""; //所选日期 以 yyyy-MM-dd 的形式保存

	Note currentNote = null; //当前的记事

	String UItype = "" ; //区分界面

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_note);
		setContentView(R.layout.activity_caledar_edit_note);
		setTitle("日程记事");

		initView();

		UItype = getIntent().getStringExtra("UItype");
		if (UItype.equals("newNote")) {
			showNewNoteUI();//显示新建界面
		}
		else if (UItype.equals("showNote")) {
			showTextNoteUI();//显示记事文字界面
		}
		else if (UItype.equals("editNote")) {
			showEditNoteUI();//显示修改记事文字界面
		}

	}

	private void showTextNoteUI() {
		currentNote = (Note) getIntent().getSerializableExtra("note");
		etNoteLayout.setVisibility(View.VISIBLE);
		etNoteTitle.setText(currentNote.title);
		etNoteContent.setText(currentNote.content);
		etNoteTitle.setEnabled(false);
		etNoteContent.setEnabled(false);
		etNoteTitle.setBackgroundColor(Color.WHITE);
		etNoteTitle.setTextColor(Color.BLACK);
		etNoteContent.setHint("");
		etNoteContent.setBackgroundColor(Color.WHITE);
		etNoteContent.setTextColor(Color.BLACK);

		btnSave.setVisibility(View.GONE);
		btnDelete.setVisibility(View.VISIBLE);
		btnEdit.setVisibility(View.VISIBLE);
	}

	private void showNewNoteUI() {
		etNoteLayout.setVisibility(View.VISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.GONE);
		btnEdit.setVisibility(View.GONE);
	}

	private void showEditNoteUI() {

		etNoteLayout.setVisibility(View.VISIBLE);
		btnSave.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.GONE);
		btnEdit.setVisibility(View.GONE);

		currentNote = (Note) getIntent().getSerializableExtra("note");
		etNoteTitle.setText(currentNote.title);
		etNoteContent.setText(currentNote.content);
	}


	private void initView() {

		etNoteLayout =  (RelativeLayout) findViewById(R.id.etNoteLayout);
		etNoteTitle  =  (EditText) findViewById(R.id.etNoteTitle);
		etNoteContent=  (EditText) findViewById(R.id.etNoteContent);

		btnSave		 =  (Button)findViewById(R.id.btnSave);
		btnDelete	 =  (Button)findViewById(R.id.btnDelete);
		btnEdit		 =  (Button)findViewById(R.id.btnEdit);



		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (UItype.equals("newNote")) {
					saveNewNote();//保存一条新记录
				}
				if (UItype.equals("editNote")) {
					saveEditNote();//保存修改的记录
				}
			}
		});

		btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deleteNote(currentNote);//删除一条新记录
			}
		});

		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				editNote(currentNote);//修改一条记录
			}
		});



	}

	/**
	 * 修改一条记录
	 */
	private void editNote(Note currentNote) {

		Intent intent = new Intent(EditCaledarNoteActivity.this,EditCaledarNoteActivity.class);
		intent.putExtra("UItype", "editNote");//修改
		intent.putExtra("note", currentNote);
		startActivity(intent);
		this.finish();
	}

	/**
	 * 保存新记录
	 */
	private void saveNewNote() {

		noteDate = getIntent().getStringExtra("noteDate");
		noteTitle 	= etNoteTitle.getText().toString().trim();
		noteContent = etNoteContent.getText().toString().trim();

		if (!noteTitle.equals("")) {
			currentNote = new Note();
			currentNote.title= noteTitle;
			currentNote.content= noteContent;
			currentNote.date= noteDate;

			DBServer.addNote(getApplicationContext(), currentNote);

			EditCaledarNoteActivity.this.finish();

		}else {
			Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
		}
	}


	/**
	 * 删除一条记录
	 */
	private void deleteNote(Note note) {
		DBServer.deleteNoteByDate(getApplicationContext(), note.id);
		EditCaledarNoteActivity.this.finish();
	}


	/**
	 * 保存修改的记录
	 */
	private void saveEditNote() {

		currentNote = (Note) getIntent().getSerializableExtra("note");
		noteTitle 	= etNoteTitle.getText().toString().trim();
		noteContent = etNoteContent.getText().toString().trim();

		if (!noteTitle.equals("")) {
			currentNote.title= noteTitle;
			currentNote.content= noteContent;

			DBServer.updateNote(getApplicationContext(), currentNote);
			EditCaledarNoteActivity.this.finish();

		}else {
			Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
		}
	}
}
