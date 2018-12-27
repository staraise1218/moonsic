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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_study_activity);
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
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    private void initData() {
        // do nothing
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
                    rlBack.setNextFocusRightId(R.id.rl_homepage);
                    rlBack.setNextFocusDownId(R.id.rl_song);
                } else {
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                    rlHomePage.setNextFocusLeftId(R.id.rl_back);
                    rlHomePage.setNextFocusDownId(R.id.rl_dance);
                } else {
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_song:
                if (b) {
                    ivSong.setImageResource(R.mipmap.finish_song_focus);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                } else {
                    ivSong.setImageResource(R.mipmap.finish_song);
                }
                break;
            case R.id.rl_dance:
                if (b) {
                    ivDance.setImageResource(R.mipmap.finish_dance_focus);
                    rlDance.setNextFocusUpId(R.id.rl_homepage);
                    rlDance.setNextFocusLeftId(R.id.rl_song);
                } else {
                    ivDance.setImageResource(R.mipmap.finish_dance);
                }
                break;
            default:
                break;
        }
    }

    boolean isFirst = true;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    ivSong.setImageResource(R.mipmap.finish_song_focus);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    ivSong.setImageResource(R.mipmap.finish_song_focus);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    ivSong.setImageResource(R.mipmap.finish_song_focus);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    ivSong.setImageResource(R.mipmap.finish_song_focus);
                    rlSong.requestFocus();
                    rlSong.setFocusable(true);
                    rlSong.setNextFocusUpId(R.id.rl_back);
                    rlSong.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    onBackPressed();
                }
                break;
                default:
                    break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
