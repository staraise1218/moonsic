package cn.baby.happyball.vedio;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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

/**
 * @author DRH
 */
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
    @BindView(R.id.iv_song_one_first)
    ImageView ivSongOneFirst;

    @BindView(R.id.tv_song_one_second)
    TextView tvSongOneSecond;
    @BindView(R.id.iv_song_one_second)
    ImageView ivSongOneSecond;

    @BindView(R.id.tv_song_one_third)
    TextView tvSongOneThird;
    @BindView(R.id.iv_song_one_third)
    ImageView ivSongOneThird;

    @BindView(R.id.tv_song_one_four)
    TextView tvSongOneFour;
    @BindView(R.id.iv_song_one_four)
    ImageView ivSongOneFour;
    ;

    @BindView(R.id.tv_song_two_first)
    TextView tvSongTwoFirst;
    @BindView(R.id.iv_song_two_first)
    ImageView ivSongTwoFirst;

    @BindView(R.id.tv_song_two_second)
    TextView tvSongTwoSecond;
    @BindView(R.id.iv_song_two_second)
    ImageView ivSongTwoSecond;

    @BindView(R.id.tv_song_two_third)
    TextView tvSongTwoThird;
    @BindView(R.id.iv_song_two_third)
    ImageView ivSongTwoThird;

    @BindView(R.id.tv_song_two_four)
    TextView tvSongTwoFour;
    @BindView(R.id.iv_song_two_four)
    ImageView ivSongTwoFour;

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.iv_song_one)
    ImageView ivSongOne;
    @BindView(R.id.iv_song_two)
    ImageView ivSongTwo;

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
        rlHomePage.setOnFocusChangeListener(this);
        rlSongAccompaniment.setOnFocusChangeListener(this);
        rlSongSing.setOnFocusChangeListener(this);

        ivSongOneFirst.setOnFocusChangeListener(this);
        ivSongOneSecond.setOnFocusChangeListener(this);
        ivSongOneThird.setOnFocusChangeListener(this);
        ivSongOneFour.setOnFocusChangeListener(this);
        ivSongTwoFirst.setOnFocusChangeListener(this);
        ivSongTwoSecond.setOnFocusChangeListener(this);
        ivSongTwoThird.setOnFocusChangeListener(this);
        ivSongTwoFour.setOnFocusChangeListener(this);
        ivSongOne.setOnFocusChangeListener(this);
        ivSongTwo.setOnFocusChangeListener(this);
    }

    public void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    public void initData() {
        obtainViewFocus(rlSongSing);
        rlSongSing.requestFocus();
        rlSongSing.setFocusable(true);
        rlSongSing.setNextFocusUpId(R.id.rl_homepage);
        rlSongSing.setNextFocusDownId(R.id.rl_song_accompaniment);

        showLoading(true);
//        final String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
        final String songUrl = "pay_attention_to_thunder_and_rain.mp3";
        new PlayMusicAsyncTask().execute(songUrl);
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
        showHorn(false);
        if (mMediaPlayer != null) {
            mSeek = mMediaPlayer.getCurrentPosition();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getAccompaniment_file())).toString();
        playMusic(songUrl);
    }

    @OnClick({R.id.iv_song_sing, R.id.rl_song_sing})
    public void onSongSing() {
        showHorn(true);
        if (mMediaPlayer != null) {
            mSeek = mMediaPlayer.getCurrentPosition();
        } else {
            mMediaPlayer = new MediaPlayer();
        }
        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
        playMusic(songUrl);
    }

    @OnClick({R.id.iv_song_one_first, R.id.rl_song_one_first})
    public void onPlayOneFirst() {
        if (ivSongOneFirst.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    private void playMusic(String songUrl) {
        showLoading(true);
        songUrl = "pay_attention_to_thunder_and_rain.mp3";
        new PlayMusicAsyncTask().execute(songUrl);
    }

    @OnClick({R.id.iv_song_one_second, R.id.rl_song_one_second})
    public void onPlayOneSecond() {
        if (ivSongOneSecond.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_one_third, R.id.rl_song_one_third})
    public void onPlayOneThird() {
        if (ivSongOneThird.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_one_four})
    public void onPlayOneFour() {
        if (ivSongOneFour.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick(R.id.iv_song_one)
    public void onPlayOne() {
        if (ivSongOne.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_two_first, R.id.rl_song_two_first})
    public void onPlayTwoFirst() {
        if (ivSongTwoFirst.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_two_second, R.id.rl_song_two_second})
    public void onPlayTwoSecond() {
        if (ivSongTwoSecond.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_two_third, R.id.rl_song_two_third})
    public void onPlayTwoThird() {
        if (ivSongTwoThird.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick({R.id.iv_song_two_four})
    public void onPlayTwoFour() {
        if (ivSongTwoFour.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @OnClick(R.id.iv_song_two)
    public void onPlayTwo() {
        if (ivSongTwo.getVisibility() == View.VISIBLE) {
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getGuide_melody_file())).toString();
            playMusic(songUrl);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_homepage:
                if (b) {
//                    obtainViewFocus(rlHomePage);
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                    rlHomePage.setNextFocusDownId(R.id.rl_song_sing);
                } else {
//                    loseViewFocus(rlHomePage);
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_song_sing:
                if (b) {
//                    obtainViewFocus(rlSongSing);
                    ivSongSing.setImageResource(R.mipmap.choice_episode_focus);
                    rlSongSing.setNextFocusDownId(R.id.rl_song_accompaniment);
                    rlSongSing.setNextFocusUpId(R.id.rl_homepage);
                } else {
//                    loseViewFocus(rlSongSing);
                    ivSongSing.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_song_accompaniment:
                if (b) {
//                    obtainViewFocus(rlSongAccompaniment);
                    ivSongAccompaniment.setImageResource(R.mipmap.choice_episode_focus);
                    rlSongAccompaniment.setNextFocusUpId(R.id.rl_song_sing);
                    rlSongAccompaniment.setNextFocusDownId(R.id.iv_song_one_first);
                    rlSongAccompaniment.setNextFocusRightId(R.id.iv_song_one_first);
                } else {
//                    loseViewFocus(rlSongAccompaniment);
                    ivSongAccompaniment.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.iv_song_one_first:
                if (b) {
                    ivSongOneFirst.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongOneFirst);
                    ivSongOneFirst.setNextFocusUpId(R.id.iv_song_accompaniment);
                    ivSongOneFirst.setNextFocusDownId(R.id.iv_song_one_second);
                } else {
                    ivSongOneFirst.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongOneFirst);
                }
                break;
            case R.id.iv_song_one_second:
                if (b) {
                    ivSongOneSecond.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongOneSecond);
                    ivSongOneSecond.setNextFocusUpId(R.id.iv_song_one_first);
                    ivSongOneSecond.setNextFocusDownId(R.id.iv_song_one_third);
                } else {
                    ivSongOneSecond.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongOneSecond);
                }
                break;
            case R.id.iv_song_one_third:
                if (b) {
                    ivSongOneThird.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongOneThird);
                    ivSongOneThird.setNextFocusUpId(R.id.iv_song_one_second);
                    ivSongOneThird.setNextFocusDownId(R.id.iv_song_one_four);
                } else {
                    ivSongOneThird.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongOneThird);
                }
                break;
            case R.id.iv_song_one_four:
                if (b) {
                    ivSongOneFour.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongOneFour);
                    ivSongOneFour.setNextFocusUpId(R.id.iv_song_one_third);
                    ivSongOneFour.setNextFocusRightId(R.id.iv_song_one);
                    ivSongOneFour.setNextFocusDownId(R.id.iv_song_two_first);
                } else {
                    ivSongOneFour.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongOneFour);
                }
                break;
            case R.id.iv_song_one:
                if (b) {
                    ivSongOne.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongOne);
                    ivSongOne.setNextFocusLeftId(R.id.iv_song_one_four);
                } else {
                    ivSongOne.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongOne);
                }
                break;
            case R.id.iv_song_two_first:
                if (b) {
                    ivSongTwoFirst.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongTwoFirst);
                    ivSongTwoFirst.setNextFocusUpId(R.id.iv_song_one_four);
                    ivSongTwoFirst.setNextFocusDownId(R.id.iv_song_two_second);
                } else {
                    ivSongTwoFirst.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongTwoFirst);
                }
                break;
            case R.id.iv_song_two_second:
                if (b) {
                    ivSongTwoSecond.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongTwoSecond);
                    ivSongTwoSecond.setNextFocusUpId(R.id.iv_song_two_first);
                    ivSongTwoSecond.setNextFocusDownId(R.id.iv_song_two_third);
                } else {
                    ivSongTwoSecond.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongTwoSecond);
                }
                break;
            case R.id.iv_song_two_third:
                if (b) {
                    ivSongTwoThird.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongTwoThird);
                    ivSongTwoThird.setNextFocusUpId(R.id.iv_song_two_second);
                    ivSongTwoThird.setNextFocusDownId(R.id.iv_song_two_four);
                } else {
                    ivSongTwoThird.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongTwoThird);
                }
                break;
            case R.id.iv_song_two_four:
                if (b) {
                    ivSongTwoFour.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongTwoFour);
                    ivSongTwoFour.setNextFocusUpId(R.id.iv_song_two_third);
                    ivSongTwoFour.setNextFocusDownId(R.id.rl_homepage);
                } else {
                    ivSongTwoFour.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongTwoFour);
                }
                break;
            case R.id.iv_song_two:
                if (b) {
                    ivSongTwo.setImageResource(R.mipmap.song_playing_pressed);
                    obtainViewFocus(ivSongTwo);
                    ivSongTwo.setNextFocusLeftId(R.id.tv_song_two_four);
                } else {
                    ivSongTwo.setImageResource(R.mipmap.song_playing_def);
                    loseViewFocus(ivSongTwo);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        super.onDestroy();
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showHorn(boolean show) {
        ivSongOneFirst.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongOneSecond.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongOneThird.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongOneFour.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongTwoFirst.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongTwoSecond.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongTwoThird.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongTwoFour.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongOne.setVisibility(show ? View.VISIBLE : View.GONE);
        ivSongTwo.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public class PlayMusicAsyncTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            if (mMediaPlayer == null){
                mMediaPlayer = new MediaPlayer();
            }
            try {
                //播放 assets 音乐文件
                AssetFileDescriptor fd = getAssets().openFd(strings[0]);
                mMediaPlayer.reset();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
                mMediaPlayer.prepare();
            } catch (Exception e) {
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(VedioSongActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
                });
            }
            mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                runOnUiThread(() -> {
                    showLoading(false);
                    mediaPlayer.start();
                });
            });
            mMediaPlayer.setOnCompletionListener(mediaPlayer -> play());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showLoading(false);
        }
    }
}