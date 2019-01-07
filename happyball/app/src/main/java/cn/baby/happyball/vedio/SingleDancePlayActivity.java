package cn.baby.happyball.vedio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.constant.SystemConfig;

/**
 * @author DRH
 */
public class SingleDancePlayActivity extends BaseActivity implements View.OnFocusChangeListener {

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

    /**
     * 下一个
     */
    @BindView(R.id.rl_dance_next)
    RelativeLayout rlDanceNext;
    @BindView(R.id.iv_dance_next)
    ImageView ivDanceNext;
    /**
     * 上一个
     */
    @BindView(R.id.iv_dance_last)
    ImageView ivDanceLast;
    @BindView(R.id.rl_dance_last)
    RelativeLayout rlDanceLast;

    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    private List<String> mSingleDanceUrls = new ArrayList<>(8);
    private int mPlayingIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_play_activity_dance);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    private void bindEvents() {
        rlHomePage.setOnFocusChangeListener(this);
        rlDanceNext.setOnFocusChangeListener(this);
        rlDanceLast.setOnFocusChangeListener(this);
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(SingleDancePlayActivity.this, VedioDanceActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(SingleDancePlayActivity.this, MainActivity.class));
    }

    @OnClick({R.id.rl_dance_next, R.id.iv_dance_next})
    public void onNext() {
        mPlayingIndex = mPlayingIndex + 1;
        playVedio(mSingleDanceUrls.get(mPlayingIndex));
        if (mPlayingIndex == mSingleDanceUrls.size() - 1) {
            rlDanceNext.setVisibility(View.GONE);
        } else {
            rlDanceNext.setVisibility(View.VISIBLE);
        }
        rlDanceLast.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.rl_dance_last, R.id.iv_dance_last})
    public void onLast() {
        mPlayingIndex = mPlayingIndex - 1;
        playVedio(mSingleDanceUrls.get(mPlayingIndex));
        if (mPlayingIndex == 0) {
            rlDanceLast.setVisibility(View.GONE);
        } else {
            rlDanceLast.setVisibility(View.VISIBLE);
        }
        rlDanceNext.setVisibility(View.VISIBLE);
    }

    /**
     * 视频切换
     *
     * @param vedioUrl
     */
    private void playVedio(String vedioUrl) {
        showLoading(true);
        new PlayMusicAsyncTask().execute(vedioUrl);
    }

    private void getData() {
        mSingleDanceUrls = getIntent().getStringArrayListExtra(SystemConfig.SINGLE_DANCE);
        mPlayingIndex = getIntent().getIntExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 0);
        if (mPlayingIndex == 0) {
            rlDanceLast.setVisibility(View.GONE);
            rlDanceNext.setVisibility(View.VISIBLE);
        } else if (mPlayingIndex == mSingleDanceUrls.size() - 1) {
            rlDanceLast.setVisibility(View.VISIBLE);
            rlDanceNext.setVisibility(View.GONE);
        } else {
            rlDanceNext.setVisibility(View.VISIBLE);
            rlDanceLast.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        showLoading(true);

        mMediaPlayer = new MediaPlayer();
        mSurfaceHolder = svPlay.getHolder();
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            mediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mp -> mMediaPlayer.start());
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
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
                    mMediaPlayer.setDataSource(SingleDancePlayActivity.this, Uri.parse(mSingleDanceUrls.get(mPlayingIndex)));
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

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_homepage:
                if (b) {
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                } else {
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_dance_next:
                if (b) {
                    ivDanceNext.setImageResource(R.mipmap.choice_episode_focus);
                } else {
                    ivDanceNext.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_dance_last:
                if (b) {
                    ivDanceLast.setImageResource(R.mipmap.choice_episode_focus);
                } else {
                    ivDanceLast.setImageResource(R.mipmap.choice_episode);
                }
                break;
            default:
                break;
        }
    }

    public class PlayMusicAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mSurfaceHolder == null) {
                mSurfaceHolder = svPlay.getHolder();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                    showLoading(false);
                    mediaPlayer.start();
                });
                mMediaPlayer.setOnCompletionListener(mp -> {
                    showLoading(false);
                    mMediaPlayer.start();
                });
            }
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDisplay(mSurfaceHolder);
                mMediaPlayer.setDataSource(SingleDancePlayActivity.this, Uri.parse(strings[0]));
                mMediaPlayer.prepareAsync();
            } catch (Exception e) {
                showLoading(false);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showLoading(false);
        }
    }
}
