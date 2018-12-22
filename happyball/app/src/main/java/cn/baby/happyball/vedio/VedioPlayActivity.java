package cn.baby.happyball.vedio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
public class VedioPlayActivity extends BaseActivity implements View.OnFocusChangeListener {

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
    @BindView(R.id.sb_play)
    SeekBar sbPlay;
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
    private Episode mEpisode;
    private int mSemesterId;
    private int mLessonId;
    private String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_play_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    private void bindEvents() {
        rlHomePage.setOnFocusChangeListener(this);
        ivPlay.setOnFocusChangeListener(this);
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
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showDuration(mediaPlayer);
            showLoading(false);
            mediaPlayer.start();
            mHandler.post(mTicker);
            ivPlay.setImageResource(R.drawable.audio_pause_selector);
        });
        mMediaPlayer.setOnCompletionListener(mp -> {
            showDuration(mMediaPlayer);
            showLoading(false);
            mMediaPlayer.start();
            ivPlay.setImageResource(R.drawable.audio_pause_selector);
        });
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
                    mMediaPlayer.reset();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
            }
        });
        svPlay.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    ivPlay.setImageResource(R.drawable.vedio_play_selector);
                } else {
                    mMediaPlayer.start();
                    ivPlay.setImageResource(R.drawable.vedio_pause_selector);
                }
            }
            return true;
        });
    }

    @OnClick(R.id.iv_play)
    public void onPlaying() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            ivPlay.setImageResource(R.drawable.vedio_play_selector);
        } else {
            mMediaPlayer.start();
            ivPlay.setImageResource(R.drawable.vedio_pause_selector);
        }
    }

    private void showDuration(MediaPlayer mediaPlayer) {
        int duration = mediaPlayer.getDuration() / 1000;
        sbPlay.setMax(mediaPlayer.getDuration());
        int minute = duration / 60;
        int second = duration % 60;
        StringBuilder builder = new StringBuilder();

        if (minute == 0) {
            builder.append("00:");
        } else if (minute > 0 && minute < 10) {
            builder.append("0").append(minute).append(":");
        } else {
            builder.append(minute).append(":");
        }

        if (second >= 0 && second < 10) {
            builder.append("0").append(second);
        } else {
            builder.append(second);
        }
        tvPlayTime.setText(builder.toString());
        ivPlay.requestFocus();
        ivPlay.setFocusable(true);
        obtainViewFocus(ivPlay);
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(mTicker);
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            obtainViewFocus(view);
        } else {
            loseViewFocus(view);
        }
    }

    private Handler mHandler = new Handler();

    private final Runnable mTicker = new Runnable() {

        @Override
        public void run() {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                sbPlay.setProgress(mMediaPlayer.getCurrentPosition());
            }

            long now = SystemClock.uptimeMillis();
            long next = now + (100 - now % 100);
            mHandler.postAtTime(mTicker, next);
        }
    };
}
