package xyx.njtech.edu.cn.diligentnode.pager;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import xyx.njtech.edu.cn.diligentnode.BaseActivity;
import xyx.njtech.edu.cn.diligentnode.R;

/**
 * Created by admin on 2018/5/16.
 */

public class AboutMeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_aboutme);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("帮助");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
