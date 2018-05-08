package xyx.njtech.edu.cn.diligentnode.contants;

import xyx.njtech.edu.cn.diligentnode.R;

/**
 * Created by admin on 2018/5/2.
 */

public class Constans {
    // 第一次使用应用
    public static boolean isFirst = true;
    // 主题
    public static int theme = R.style.NoActionBar_Theme7;

    // 当前选中的便签夹
    public static int currentFolder = FolderListConstans.ITEM_ALL;

    // 便签显示样式
    public static int noteListShowMode = NoteListConstans.MODE_GRID;

    // 是否已启用废纸篓
    public static boolean isUseRecycleBin = true;

    // 是否已设置隐私密码
    public static boolean isLocked = false;

    // 隐私密码
    public static String lockPassword="";

    /*--------------以下是sharderPreference的储存的key值---------------------*/
    // 为了兼容旧版本 一下key值命名风格可能不统一

    public static final String IS_FIRST = "is_first";

    public static final String THEME = "theme";

    public static final String CURRENT_FOLDER = "current_folder";

    public static final String NOTE_LIST_SHOW_MODE = "note_list_show_mode";

    public static final String LOCK_PASSWORD = "lock_password";

    public static final String IS_USE_RECYCLE = "is_use_recycle";

    public static final String IS_LOCKED = "is_locked";
}
