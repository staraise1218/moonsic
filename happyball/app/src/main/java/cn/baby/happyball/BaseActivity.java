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

    /**
     * 获取网络图片
     *
     * @param imageUrl
     * @param imageView
     * @param resouces
     */
    protected void loadImage(String imageUrl, final ImageView imageView, final int resouces) {
        final Bitmap frameBitmap = createSemesterBitmap(BitmapFactory.decodeResource(getResources(), resouces));
        imageView.setTag(imageUrl);
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                imageView.setImageResource(resouces);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                imageView.setImageResource(resouces);
                frameBitmap.recycle();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (imageUri.equalsIgnoreCase((String) view.getTag()) && loadedImage != null) {
                    loadedImage = createSemesterBitmap(loadedImage);
                    Bitmap bitmap = AlphaFilter.overlay(loadedImage, frameBitmap);
                    ((ImageView) view).setImageBitmap(bitmap);
                }
                frameBitmap.recycle();
                loadedImage.recycle();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                //do noting
            }
        });
    }

    /**
     * 处理240*180图片
     *
     * @param bitmapRes
     * @param frameRes
     * @return
     */
    public Bitmap loadBitmap(int bitmapRes, int frameRes) {
        Bitmap frameBitmap = createSemesterBitmap(BitmapFactory.decodeResource(getResources(), frameRes));
        Bitmap urlBitmap = createSemesterBitmap(BitmapFactory.decodeResource(getResources(), bitmapRes));
        Bitmap bitmap = AlphaFilter.overlay(urlBitmap, frameBitmap);
        urlBitmap.recycle();
        frameBitmap.recycle();
        return bitmap;
    }

    private static final int SEMESTER_BITMAP_WIDTH = 240;
    private static final int SEMESTER_BITMAP_HEIGHT = 180;
    public Bitmap createSemesterBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = DpPixUtils.dp2px(getApplicationContext(), SEMESTER_BITMAP_WIDTH);
        int newHeight = DpPixUtils.dp2px(getApplicationContext(), SEMESTER_BITMAP_HEIGHT);

        if (width < newWidth || height < newHeight) {
            return bitmap;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
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

    public Bitmap loadStudyBitmap(int bitmapRes, int frameRes) {
        Bitmap frameBitmap = createStudyBitmap(BitmapFactory.decodeResource(getResources(), frameRes));
        Bitmap studyBitmap = createStudyBitmap(BitmapFactory.decodeResource(getResources(), bitmapRes));
        Bitmap bitmap = AlphaFilter.overlay(studyBitmap, frameBitmap);
        studyBitmap.recycle();
        frameBitmap.recycle();
        return bitmap;
    }

    private static final int STUDY_BITMAP_WIDTH = 250;
    private static final int STUDY_BITMAP_HEIGHT = 350;
    public Bitmap createStudyBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = DpPixUtils.dp2px(getApplicationContext(), STUDY_BITMAP_WIDTH);
        int newHeight = DpPixUtils.dp2px(getApplicationContext(), STUDY_BITMAP_HEIGHT);

        if (width < newWidth || height < newHeight) {
            return bitmap;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    protected static Bitmap mReceptionLastBitmap;
    protected static Bitmap mReceptionNextBitmap;
    protected static Bitmap mMiddleLastBitmap;
    protected static Bitmap mMiddleNextBitmap;
    protected static Bitmap mBigLastBitmap;
    protected static Bitmap mBigNextBitmap;
    protected class LoadBitmapAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private ILoadBitmapListener mLoadBitmapListener;

        public LoadBitmapAsyncTask(ILoadBitmapListener loadBitmapListener) {
            mLoadBitmapListener = loadBitmapListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLoadBitmapListener != null) {
                mLoadBitmapListener.onReady();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (mReceptionLastBitmap == null) {
                mReceptionLastBitmap = loadBitmap(R.mipmap.timg_1, R.mipmap.main_reception_last);
            }
            if (mReceptionNextBitmap == null) {
                mReceptionNextBitmap = loadBitmap(R.mipmap.timg_2, R.mipmap.main_reception_next);
            }
            if (mMiddleLastBitmap == null) {
                mMiddleLastBitmap = loadBitmap(R.mipmap.timg_3, R.mipmap.main_middle_last);
            }
            if (mMiddleNextBitmap == null) {
                mMiddleNextBitmap = loadBitmap(R.mipmap.timg_4, R.mipmap.main_middle_next);
            }
            if (mBigLastBitmap == null) {
                mBigLastBitmap = loadBitmap(R.mipmap.timg_5, R.mipmap.main_big_last);
            }
            if (mBigNextBitmap == null) {
                mBigNextBitmap = loadBitmap(R.mipmap.timg_6, R.mipmap.main_big_next);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mLoadBitmapListener != null) {
                mLoadBitmapListener.onComplete();
            }
        }
    }

    protected static Bitmap mStudyBitmap;
    protected static Bitmap mKnowledgeBitmap;
    public class LoadStudyBitmap extends AsyncTask<Void, Void, Boolean> {

        private ILoadBitmapListener mLoadBitmapListener;

        public LoadStudyBitmap(ILoadBitmapListener loadBitmapListener) {
            mLoadBitmapListener = loadBitmapListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mLoadBitmapListener != null) {
                mLoadBitmapListener.onReady();
            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (mStudyBitmap == null) {
                mStudyBitmap = loadStudyBitmap(R.mipmap.study, R.mipmap.finish_song);
            }
            if (mKnowledgeBitmap == null) {
                mKnowledgeBitmap = loadBitmap(R.mipmap.knowledge, R.mipmap.finish_dance);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (mLoadBitmapListener != null) {
                mLoadBitmapListener.onComplete();
            }
        }
    }

    public interface ILoadBitmapListener {
        void onReady();
        void onComplete();
    }

}
