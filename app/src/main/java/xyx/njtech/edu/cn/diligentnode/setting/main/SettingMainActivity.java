package xyx.njtech.edu.cn.diligentnode.setting.main;

import android.view.MenuItem;

import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.lock.base.MemoBaseActivity;
import xyx.njtech.edu.cn.diligentnode.setting.setting.SettingFragment;

public class SettingMainActivity extends MemoBaseActivity<ISettingMainView,SettingMainPresenter> implements ISettingMainView {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingMainPresenter initPresenter() {
        SettingMainPresenter presenter=new SettingMainPresenter();
        presenter.attch(this);
        return presenter;
    }

    @Override
    protected void initViews() {
        getSupportActionBar().setTitle("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(R.id.frame_setting_content,new SettingFragment()).commit();
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
