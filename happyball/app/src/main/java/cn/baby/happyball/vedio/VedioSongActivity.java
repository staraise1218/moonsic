package cn.baby.happyball.vedio;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;

public class VedioSongActivity extends BaseActivity implements View.OnFocusChangeListener {

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
     * 伴唱
     */
    @BindView(R.id.rl_song_accompaniment)
    RelativeLayout rlSongAccompaniment;
    @BindView(R.id.iv_song_accompaniment)
    ImageView ivSongAccompaniment;

    /**
     * 导唱
     */
    @BindView(R.id.rl_song_sing)
    RelativeLayout rlSongSing;
    @BindView(R.id.iv_song_sing)
    ImageView ivSongSing;

    /**
     * 歌词
     */
    @BindView(R.id.tv_song_one_first)
    TextView tvSongOneFirst;
    @BindView(R.id.tv_song_one_second)
    TextView tvSongOneSecond;
    @BindView(R.id.tv_song_one_third)
    TextView tvSongOneThird;
    @BindView(R.id.tv_song_one_four)
    TextView tvSongOneFour;
    @BindView(R.id.tv_song_two_first)
    TextView tvSongTwoFirst;
    @BindView(R.id.tv_song_two_second)
    TextView tvSongTwoSecond;
    @BindView(R.id.tv_song_two_third)
    TextView tvSongTwoThird;
    @BindView(R.id.tv_song_two_four)
    TextView tvSongTwoFour;

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Episode mEpisode;
    private MediaPlayer mMediaPlayer;
    private int mSeek;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_song_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    public void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);
        rlSongAccompaniment.setOnFocusChangeListener(this);
        rlSongSing.setOnFocusChangeListener(this);
    }

    public void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    public void initData() {
        showLoading(true);
        final String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 使用唤醒锁
//            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(songUrl));
            mMediaPlayer.prepare();
        } catch (Exception e) {
            showLoading(false);
            Toast.makeText(VedioSongActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
        }
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            mediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mediaPlayer -> play() );
//        obtainViewFocus(rlSongSing);
//        rlSongSing.requestFocus();
//        rlSongSing.setFocusable(true);
    }

    private void play() {
        try {
            showLoading(false);
            if (mSeek != 0) {
                mMediaPlayer.seekTo(mSeek);
            }
            mMediaPlayer.reset();
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {

        }
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioSongActivity.this, VedioStudyActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioSongActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_song_accompaniment, R.id.rl_song_accompaniment})
    public void onSongAccompaniment() {
        showLoading(true);
        if (mMediaPlayer != null) {
            mSeek = mMediaPlayer.getCurrentPosition();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getAccompaniment_file())).toString();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(songUrl));
            mMediaPlayer.prepare();
        } catch (Exception e) {
            showLoading(false);
            Toast.makeText(VedioSongActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
        }
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            if (mSeek != 0) {
                mediaPlayer.seekTo(mSeek);
            }
            mediaPlayer.start();
        });
    }

    @OnClick({R.id.iv_song_sing, R.id.rl_song_sing})
    public void onSongSing() {
        showLoading(true);
        if (mMediaPlayer != null) {
            mSeek = mMediaPlayer.getCurrentPosition();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(songUrl));
            mMediaPlayer.prepare();
        } catch (Exception e) {
            showLoading(false);
            Toast.makeText(VedioSongActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
        }
        mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
            showLoading(false);
            if (mSeek != 0) {
                mediaPlayer.seekTo(mSeek);
            }
            mediaPlayer.start();
        });
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            obtainViewFocus(view);
        } else {
            loseViewFocus(view);
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
