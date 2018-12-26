package cn.baby.happyball;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.util.AlphaFilter;
import cn.baby.happyball.util.DpPixUtils;

public class BaseActivity extends Activity {

    private SharedPreferences mPreferences;
    private DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getSharedPreferences(SystemConfig.SYSTEM_CONFIG, MODE_PRIVATE);
        options=new DisplayImageOptions.Builder()
                //使用内存缓存
                .cacheInMemory(true)
                //使用SD卡缓存
                .cacheOnDisk(true)
                //设置图片格式
                .bitmapConfig(Bitmap.Config.RGB_565)
                //设置圆角图片
                .displayer(new RoundedBitmapDisplayer(8))
                .build();
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
            ViewCompat.animate(view).scaleX(1.00f).scaleY(1.00f).translationZ(1).start();
        } else {
            ViewCompat.animate(view).scaleX(1.00f).scaleY(1.00f).start();
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

    /**
     * 获取网络图片
     *
     * @param imageUrl
     * @param imageView
     */
    protected void loadImage(String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (imageUri.equalsIgnoreCase((String) view.getTag()) && loadedImage != null) {
                    ((ImageView) view).setImageBitmap(loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                //do noting
            }
        });
    }

}
