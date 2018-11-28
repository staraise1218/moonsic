package cn.baby.happyball.vedio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.util.AlphaFilter;

/**
 * @author DRH
 */
public class VedioStudyActivity extends BaseActivity implements View.OnFocusChangeListener {

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    /**
     * 返回
     */
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    /**
     * 学唱歌
     */
    @BindView(R.id.rl_song)
    RelativeLayout rlSong;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    /**
     * 学跳舞
     */
    @BindView(R.id.rl_dance)
    RelativeLayout rlDance;
    @BindView(R.id.iv_dance)
    ImageView ivDance;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Episode mEpisode;
    private Bitmap song;
    private Bitmap dance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_study);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    private void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);
        rlSong.setOnFocusChangeListener(this);
        rlDance.setOnFocusChangeListener(this);
    }

    private void getData() {
        showLoading(true);
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    private void initData() {
        new LoadImagerAsytask().execute();
    }

    @OnClick({R.id.iv_song, R.id.rl_song})
    public void onSong() {
        startActivity(new Intent(VedioStudyActivity.this, VedioSongActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @OnClick({R.id.iv_dance, R.id.rl_dance})
    public void onDance() {
        startActivity(new Intent(VedioStudyActivity.this, VedioDanceActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioStudyActivity.this, VedioFinishActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioStudyActivity.this, MainActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_back:
                if (b) {
                    obtainViewFocus(rlBack);
                    rlBack.setNextFocusRightId(R.id.rl_homepage);
                    rlBack.setNextFocusDownId(R.id.rl_song);
                } else {
                    loseViewFocus(rlBack);
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlHomePage.setNextFocusLeftId(R.id.rl_back);
                    rlHomePage.setNextFocusDownId(R.id.rl_dance);
                } else {
                    loseViewFocus(rlHomePage);
                }
                break;
            case R.id.rl_song:
                if (b) {
                    obtainViewFocus(rlSong);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                } else {
                    loseViewFocus(rlSong);
                }
                break;
            case R.id.rl_dance:
                if (b) {
                    obtainViewFocus(rlDance);
                    rlDance.setNextFocusUpId(R.id.rl_homepage);
                    rlDance.setNextFocusLeftId(R.id.rl_song);
                } else {
                    loseViewFocus(rlDance);
                }
                break;
            default:
                if (b) {
                    obtainViewFocus(view);
                } else {
                    loseViewFocus(view);
                }
                break;
        }
    }

    boolean isFirst = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                startActivity(new Intent(VedioStudyActivity.this, VedioSongActivity.class)
                        .putExtra(SystemConfig.EPISODE, mEpisode));
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isFirst) {
                    obtainViewFocus(rlSong);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (isFirst) {
                    obtainViewFocus(rlSong);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (isFirst) {
                    obtainViewFocus(rlSong);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isFirst) {
                    obtainViewFocus(rlSong);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private class LoadImagerAsytask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap songFrame = createStudyBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_finish_song));
            Bitmap songBitmap = createStudyBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_study));
            song = AlphaFilter.overlay(songBitmap, songFrame);
            songBitmap.recycle();
            songFrame.recycle();

            Bitmap danceFrame = createStudyBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_finish_dance));
            Bitmap danceBitmap = createStudyBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_knowledge));
            dance = AlphaFilter.overlay(danceBitmap, danceFrame);
            danceBitmap.recycle();
            danceFrame.recycle();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ivSong.setImageBitmap(song);
            ivDance.setImageBitmap(dance);
            showLoading(false);
        }
    }
}
