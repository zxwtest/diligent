package xyx.njtech.edu.cn.diligentnode.pager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.utils.BusProvider;
import xyx.njtech.edu.cn.diligentnode.utils.Events;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutMePager extends BasePager implements View.OnClickListener {

    public AboutMePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.aboutme_pager, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
    }

    @Override
    public void onClick(View v) {
    }
}
