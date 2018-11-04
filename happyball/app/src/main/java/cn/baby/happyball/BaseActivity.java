package cn.baby.happyball;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

import cn.baby.happyball.constant.SystemConfig;

public class BaseActivity extends Activity {

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(SystemConfig.SYSTEM_CONFIG, MODE_PRIVATE);
    }

    public void setValue(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public String getValue(String key) {
        return mPreferences.getString(key, "");
    }

    /**
     * 获取焦点
     *
     * @param view
     */
    public void obtainViewFocus(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //抬高Z轴
            ViewCompat.animate(view).scaleX(1.10f).scaleY(1.10f).translationZ(1).start();
        } else {
            ViewCompat.animate(view).scaleX(1.10f).scaleY(1.10f).start();
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    /**
     * 失去焦点
     *
     * @param view
     */
    public void loseViewFocus(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //抬高Z轴
            ViewCompat.animate(view).scaleX(1.00f).scaleY(1.00f).translationZ(0).start();
        } else {
            ViewCompat.animate(view).scaleX(1.00f).scaleY(1.00f).start();
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }
}
