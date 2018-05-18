package xyx.njtech.edu.cn.diligentnode.contants;


import xyx.njtech.edu.cn.diligentnode.utils.PreferencesUtil;

public class CacheManager {
    /**
     *   是否设置了私密密码
     */
    public static void setAndSaveIsLocked(boolean isLocked){
        Constans.isLocked=isLocked;
        PreferencesUtil.saveBoolean(Constans.IS_LOCKED,isLocked);
    }

    /**
     *   私密密码
     */
    public static void setAndSaveLockPassword(String lockPassword){
        Constans.lockPassword=lockPassword;
        PreferencesUtil.saveString(Constans.LOCK_PASSWORD,lockPassword);
    }
}
