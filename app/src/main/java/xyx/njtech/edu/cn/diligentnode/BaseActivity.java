package xyx.njtech.edu.cn.diligentnode;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * 描述：所有Activity基类
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.mContext = this;
        Log.d("BaseActivity", "========activity name = [" + getClass().getSimpleName() + "]");
    }

    public void showToast(String text) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void initViews(){

    };
}
