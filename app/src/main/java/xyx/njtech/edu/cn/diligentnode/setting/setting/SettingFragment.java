package xyx.njtech.edu.cn.diligentnode.setting.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import xyx.njtech.edu.cn.diligentnode.R;
import xyx.njtech.edu.cn.diligentnode.contants.Constans;
import xyx.njtech.edu.cn.diligentnode.setting.main.SettingMainActivity;
import xyx.njtech.edu.cn.diligentnode.utils.PreferencesUtil;
import xyx.njtech.edu.cn.diligentnode.utils.ThemeUtils;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SettingMainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        mActivity = (SettingMainActivity) getActivity();
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case Constans.THEME:
                changeTheme();
                break;
        }
    }

    private void changeTheme(){
        int newTheme = PreferencesUtil.getInt(Constans.THEME,Constans.theme);
        if (newTheme != Constans.theme && mActivity != null) {
            Constans.theme=newTheme;
            mActivity.setTheme(newTheme);
            ThemeUtils.resetToolbarColor(mActivity);
            ThemeUtils.resetWindowStatusBarColor(mActivity);
            this.onCreate(null);
        }
    }
}