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
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;

/**
 * @author DRH
 */
public class VedioPlayActivity extends BaseActivity {

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
    //    @BindView(R.id.vv_play)
//    VideoView videoPlay;
    @BindView(R.id.sv_play)
    SurfaceView svPlay;

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private MediaPlayer mMediaPlayer;
    private Episode mEpisode;
    private int mSemesterId;
    private int mLessonId;
    private String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_play_activity);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    public void onPlay() {
        setValue(mKey, getString(R.string.played));
        startActivity(new Intent(VedioPlayActivity.this, VedioFinishActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        setValue(mKey, String.valueOf(mMediaPlayer.getCurrentPosition()));
        startActivity(new Intent(VedioPlayActivity.this, VedioChoiceActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        setValue(mKey, String.valueOf(mMediaPlayer.getCurrentPosition()));
        startActivity(new Intent(VedioPlayActivity.this, MainActivity.class));
    }

    private void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
        mSemesterId = getIntent().getIntExtra(SystemConfig.SEMESTER, 1);
        mLessonId = getIntent().getIntExtra(SystemConfig.LESSON, 1);
        StringBuilder builder = new StringBuilder();
        builder.append(SystemConfig.EPISODE_TIME).append(mSemesterId).append(mLessonId);
        mKey = builder.toString();
    }

    private void initData() {
        showLoading(true);
        final String videoUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getVideofile())).toString();
        mMediaPlayer = new MediaPlayer();
        SurfaceHolder surfaceHolder = svPlay.getHolder();
        // 使用唤醒锁
//        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            mediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mp -> mMediaPlayer.start());
        mMediaPlayer.setOnCompletionListener(mediaPlayer -> onPlay());
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
                    mMediaPlayer.setOnCompletionListener(mediaPlayer -> onPlay());
                }
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDisplay(surfaceHolder);
                    mMediaPlayer.setDataSource(VedioPlayActivity.this, Uri.parse(videoUrl));
//                    mMediaPlayer.prepare();
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
//        videoPlay.setVideoPath(videoUrl);
//        videoPlay.setMediaController(new MediaController(this));
//        videoPlay.setOnPreparedListener(mp -> {
//            mp.setOnInfoListener((mp1, what, extra) ->  {
//                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
//                        videoPlay.setBackgroundColor(Color.TRANSPARENT);
//                    }
//                return true;
//            });
//            videoPlay.start();
//        });
//        videoPlay.setOnCompletionListener(mp -> videoPlay.start());
//        videoPlay.setOnCompletionListener(mediaPlayer -> onPlay());
//        videoPlay.requestFocus();
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }
}
