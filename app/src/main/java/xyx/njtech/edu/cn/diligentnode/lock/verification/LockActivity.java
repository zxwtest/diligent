package xyx.njtech.edu.cn.diligentnode.lock.verification;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import java.util.List;

import butterknife.Bind;
import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.contants.Constans;
import xyx.njtech.edu.cn.diligentnode.lock.base.MemoBaseActivity;
import xyx.njtech.edu.cn.diligentnode.setting.lock.LockSettingActivity;
import xyx.njtech.edu.cn.diligentnode.widget.LockView;

public class LockActivity extends MemoBaseActivity<ILockView, LockPresenter> implements ILockView, LockView.OnDrawFinishedListener
{

    @Bind(R.id.lockview_lock)
    LockView mLickView;

    @Bind(R.id.tv_lock_tip)
    TextView mTvTip;

    @Bind(R.id.ll_lock)
    LinearLayout mLlRoot;

    @Bind(R.id.btn_lock_ok)
    Button mBtnOk;

    @Bind(R.id.btn_lock_redraw)
    Button mBtnReDraw;

    private String mTitle;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_lock;
    }

    @Override
    protected LockPresenter initPresenter() {
        mPresenter = new LockPresenter();
        mPresenter.attch(this);

        return mPresenter;
    }

    @Override
    protected void initBeforeSetContentView() {
        initPushInAnim();
    }

    private void initPushInAnim() {

        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        TransitionInflater inflater = TransitionInflater.from(mContext);
        Transition pushDownIn = inflater.inflateTransition(R.transition.explode_in);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setEnterTransition(pushDownIn); // 第一次进入时使用
            window.setReenterTransition(pushDownIn); // 再次进入时使用
            window.setExitTransition(pushDownIn);
        }

    }

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        setTitle(mTitle);
        mTvTip.setText("请输入隐私密码");
        mLlRoot.setVisibility(View.GONE);

        mLickView.setOnDrawFinishedListener(this);
    }

    private void initData() {
        if (getIntent().getStringExtra("title") != null) {
            mTitle = getIntent().getStringExtra("title");
        } else {
            mTitle = "验证密码";
        }
    }

    @Override
    protected void updateViews() {

    }

    @Override
    public void onError() {
        System.out.println("========enter 请重试========");
        mTvTip.setText("请重试");
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvTip, "translationX", -SizeUtils.dp2px(8), SizeUtils.dp2px(8), 0);
        animator.setDuration(200);
        animator.start();
    }

    @Override
    public void onSuccess() {
        setResult(RESULT_OK, new Intent());
        onBackPressed();
    }

    @Override
    public boolean onDrawFinished(List<Integer> passPositions) {
        System.out.println("===========lockPassword===["+Constans.LOCK_PASSWORD+"],passPositions=["+passPositions+"]");
        return mPresenter.verifyPassword(passPositions, Constans.lockPassword);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("LockActivity", "===========id====="+id);
        switch (id) {
            case R.id.menu_edit_lock:
                toLockSetting();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void toLockSetting() {
        System.out.println("========mContext=========="+mContext);
        Intent intent = new Intent(mContext, LockSettingActivity.class);
        startActivity(intent);
    }

}
