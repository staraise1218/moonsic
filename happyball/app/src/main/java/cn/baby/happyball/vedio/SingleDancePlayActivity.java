package cn.baby.happyball.vedio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.bean.SingleDance;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;

/**
 * @author DRH
 */
public class SingleDancePlayActivity extends BaseActivity {

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomaPage;
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
     * 视频播放
     */
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_play_number)
    TextView tvPlayNumber;
    @BindView(R.id.pb_play)
    ProgressBar pbPlay;
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime;
    @BindView(R.id.sv_play)
    SurfaceView svPlay;

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private MediaPlayer mMediaPlayer;
    private SingleDance mSingleDance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(SingleDancePlayActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(SingleDancePlayActivity.this, MainActivity.class));
    }

    private void getData() {
        mSingleDance = (SingleDance) getIntent().getSerializableExtra(SystemConfig.SINGLEDANCE);
    }

    private void initData() {
        showLoading(true);
        final String videoUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mSingleDance.getVideo())).toString();
        mMediaPlayer = new MediaPlayer();
        SurfaceHolder surfaceHolder = svPlay.getHolder();
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            mediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mp -> mMediaPlayer.start());
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                    surfaceHolder = svPlay.getHolder();
                    mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                        showLoading(false);
                        mediaPlayer.start();
                    });
                    mMediaPlayer.setOnCompletionListener(mp -> mMediaPlayer.start());
                }
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDisplay(surfaceHolder);
                    mMediaPlayer.setDataSource(SingleDancePlayActivity.this, Uri.parse(videoUrl));
                    mMediaPlayer.prepareAsync();
                } catch (Exception e) {
                    showLoading(false);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
            }
        });
        svPlay.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.pause();
                    } else {
                        mMediaPlayer.start();
                    }
                    break;
            }
            return true;
        });
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
