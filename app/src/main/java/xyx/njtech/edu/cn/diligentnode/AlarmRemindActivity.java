package xyx.njtech.edu.cn.diligentnode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xyx.njtech.edu.cn.diligentnode.AlarmBean;

public class AlarmRemindActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        AlarmBean alarmBean = (AlarmBean) bundle.getSerializable("alarm");

        Toast.makeText(this,"闹钟响了！"+alarmBean.getTitle(),Toast.LENGTH_SHORT).show();
    }

}
