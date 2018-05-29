package xyx.njtech.edu.cn.diligentnode;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import xyx.njtech.edu.cn.diligentnode.contants.NoteListConstans;
import xyx.njtech.edu.cn.diligentnode.customview.calendar.CalendarView;
import xyx.njtech.edu.cn.diligentnode.fragment.ContentFragment;
import xyx.njtech.edu.cn.diligentnode.lock.modification.LockModificationActivity;
import xyx.njtech.edu.cn.diligentnode.lock.verification.LockActivity;
import xyx.njtech.edu.cn.diligentnode.utils.BusProvider;
import xyx.njtech.edu.cn.diligentnode.utils.CalendarManager;
import xyx.njtech.edu.cn.diligentnode.contants.Constans;
import xyx.njtech.edu.cn.diligentnode.utils.Events;
import xyx.njtech.edu.cn.diligentnode.utils.PrefUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyx.njtech.edu.cn.diligentnode.utils.PreferencesUtil;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {
    public static final int REQUEST_TO_LOCK = 1;

    public static final int REQUEST_TO_MODIFY_LOCK = 2;

    //侧边栏布局
    @Bind(R.id.main_draw_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_draw)
    NavigationView mNavigationView;
    //悬浮按钮
    @Bind(R.id.fab_layout)
    RapidFloatingActionLayout fab_layout;
    @Bind(R.id.fab_button_group)
    RapidFloatingActionButton fab_button_group;
    private RapidFloatingActionHelper rfabHelper;
    //日历View
    @Bind(R.id.calendar_view)
    public CalendarView calendar_view;
    //CalendarView自定义控件的属性
    private int mCalendarHeaderColor, mCalendarDayTextColor, mCalendarPastDayTextColor, mCalendarCurrentDayColor;
    //月份
    @Bind(R.id.title_day)
    TextView title_day;
    //提醒列表 ListView
    @Bind(R.id.main_frame)
    FrameLayout main_frame;

    //left_menu点击打开关闭侧边栏
    @OnClick(R.id.left_menu)
    void openLeftDrawe() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    //旋转箭头
    @Bind(R.id.title_arrow)
    ImageView title_arrow;
    //打开关闭CalendarView
    private boolean isOpen = true;//日历视图是否显示

    @OnClick(R.id.calendar_open_close)
    void openAndCloseCalendarView() {
        if (!isOpen) {
            calendar_view.setVisibility(View.VISIBLE);
            arrowRotateAnim(isOpen);
            isOpen = true;
        } else {
            calendar_view.setVisibility(View.GONE);
            arrowRotateAnim(isOpen);
            isOpen = false;
        }
    }

    //回到今天
    @OnClick(R.id.go_today)
    void goToday() {
        BusProvider.getInstance().send(new Events.GoBackToDay());
        calendar_view.scrollToDate(CalendarManager.getInstance().getToday(), CalendarManager.getInstance().getWeeks());
    }

    //天气
    @OnClick(R.id.go_weather)
    void goWeather() {
        Intent intent = new Intent(MainActivity.this, WeatherMainActivity.class);
        startActivity(intent);
    }

    private boolean isAllowAlert = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initCalendarInfo();
        initLayoutView();
        setMonthTitle();
        initFab();

        Utils.init(getApplicationContext());

        getCacheData();
    }

    //权限申请相关方法
    private static final int REQUEST_CODE = 1;
    @TargetApi(Build.VERSION_CODES.M)
    private void requestAlertWindowPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void getCacheData(){
        Constans.isLocked= PreferencesUtil.getBoolean(Constans.IS_LOCKED,Constans.isLocked);
        Constans.lockPassword=PreferencesUtil.getString(Constans.LOCK_PASSWORD,"");
    }

    /**
     * 权限申请弹窗
     */
    private void showPermissionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("弹窗需要开启权限")
                .setPositiveButton("开启", (dialog, which) -> {
                    requestAlertWindowPermission();
                })
                .setNegativeButton("取消", (dialog, which) -> {

                });
        builder.create().show();
    }


    /**
     * 初始化布局
     */
    private void initLayoutView() {
        //初始化 CalendarView,设置颜色
        mCalendarHeaderColor = R.color.calendar_header_day_background;
        mCalendarDayTextColor = R.color.calendar_text_day;
        mCalendarCurrentDayColor = R.color.calendar_text_current_day;
        mCalendarPastDayTextColor = R.color.calendar_text_past_day;

        calendar_view.findViewById(R.id.cal_day_names).setBackgroundColor(getResources().getColor(mCalendarHeaderColor));

        // CalendarView 初始化,完成布局的初始化，数据的绑定,日期的显示
        calendar_view.init(CalendarManager.getInstance(this), getResources().getColor(mCalendarDayTextColor), getResources()
                .getColor(mCalendarCurrentDayColor), getResources().getColor(mCalendarPastDayTextColor));

        //填充 Fragment
        FragmentManager manager =getFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();

        ContentFragment fragment = new ContentFragment();

        transaction.replace(R.id.main_frame, fragment, "CONTENT_FRAGMENT");

        transaction.commit();

    }

    /**
     * title_arrow 旋转动画
     */
    private void arrowRotateAnim(Boolean isOpen) {
        RotateAnimation anim;
        if (isOpen) {
            anim = new RotateAnimation(0, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            anim = new RotateAnimation(180f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        anim.setDuration(150);
        anim.setFillAfter(true);
        title_arrow.startAnimation(anim);
    }

    /**
     * 设置月份
     */
    private void setMonthTitle() {
        BusProvider.getInstance().toObserverable().subscribe(event -> {
            if (event instanceof Events.CalendarMonthEvent) {
                Events.CalendarMonthEvent monthEvent = (Events.CalendarMonthEvent) event;
                title_day.setText(monthEvent.getMonth());
            }
        });
    }

    /**
     * 初始化 Fab
     */
    private void initFab() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();

        items.add(new RFACLabelItem<Integer>()
                .setLabel("私密")
                .setResId(R.drawable.ic_lock_white_18dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("便签")
                .setResId(R.drawable.ic_format_list_bulleted_white_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("普通")
                .setResId(R.drawable.ic_create_white_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("提醒")
                .setResId(R.drawable.ic_access_alarms_white_24dp)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelSizeSp(14)
                .setWrapper(0)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;
        rfabHelper = new RapidFloatingActionHelper(
                this,
                fab_layout,
                fab_button_group,
                rfaContent
        ).build();

    }

    //添加事件
    @Override
    public void onRFACItemLabelClick(int i, RFACLabelItem rfacLabelItem) {

    }

    @Override
    public void onRFACItemIconClick(int i, RFACLabelItem rfacLabelItem) {
        if (i == 3) {
            Intent intent = new Intent(MainActivity.this, AddScheduleActivity.class);
            intent.putExtra("type","mainToAdd");
            startActivity(intent);
            finish();
        }else if (i == 2) {
            Intent intent = new Intent(MainActivity.this, NoteMainActivity.class);
            intent.putExtra("groupId", 1);
            startActivity(intent);
        }else if (i == 1) {
            Intent intent = new Intent(MainActivity.this, NoteMainActivity.class);
            intent.putExtra("groupId", 2);
            intent.putExtra("flag", 0);
            startActivity(intent);
        }else if(i == 0) {
            Intent intent;
            if (Constans.isLocked) {
                intent = new Intent(MainActivity.this, LockActivity.class);
            } else {
                intent = new Intent(MainActivity.this, LockModificationActivity.class);
            }

            // 5.0及以上则使用Activity动画
            if (Build.VERSION.SDK_INT >= 21) {
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivityForResult(intent, NoteListConstans.REQUEST_CODE_LOCK, bundle);
            } else {
                startActivityForResult(intent, NoteListConstans.REQUEST_CODE_LOCK);
            }
        }
    }

    private void startActivityForAnim(Intent intent,int requestCode){
        // 5.0及以上则使用Activity动画
        if(Build.VERSION.SDK_INT>=21){
            Bundle bundle= ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle();
            MainActivity.this.startActivityForResult(intent, requestCode, bundle);
        } else {
            MainActivity.this.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        support.deactivate();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            Intent intent = new Intent(MainActivity.this, NoteMainActivity.class);
            intent.putExtra("groupId", 3);
            startActivity(intent);
        } else if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this,"弹窗权限开启！",Toast.LENGTH_SHORT).show();
                PrefUtils.setBoolean(MainActivity.this, "isAllowAlert", true);
            }else {
                PrefUtils.setBoolean(MainActivity.this, "isAllowAlert", false);
            }
        }
    }


    /**
     * 初始化日历信息
     */
    private void initCalendarInfo() {
        // 设置日历显示的时间，最大为当前时间+1年，最小为当前时间-2月
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -10);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);
        //根据你传入的开始结束值，构建生成Calendar数据（各种Item，JavaBean）
        CalendarManager.getInstance(this).buildCal(minDate, maxDate, Locale.getDefault());
    }

    /**
     * 得到侧滑菜单
     */
    public NavigationView getNavigationView(){
        return mNavigationView;
    }
    /**
     * 得到DrawerLayout
     */
    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }

}