package cn.baby.happyball.audio;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.audio.adapter.AudioListAdapter;
import cn.baby.happyball.bean.Audio;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DRH
 */
public class AudioChoiceActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final String LESSON_ID = "lesson_id";

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;

    /**
     * 难度
     */
    @BindView(R.id.iv_difficult)
    ImageView ivDifficult;
    @BindView(R.id.tv_difficult)
    TextView tvDifficult;
    @BindView(R.id.rl_difficult)
    RelativeLayout rlDifficult;

    /**
     * 难度描述
     */
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;
    @BindView(R.id.rl_difficult_detail)
    RelativeLayout rlDifficultDetail;

    /**
     * 暂停播放
     */
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;

    /**
     * 音频列表
     */
    @BindView(R.id.lv_audio_list)
    ListView lvAudioList;

    /**
     * 音频列表切换
     */
    @BindView(R.id.iv_audio_left)
    ImageView ivAudioLeft;
    @BindView(R.id.iv_audio_right)
    ImageView ivAudioRight;

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private int currentPosition = -1;
    private int mLessonId;
    private List<Audio> mAudios = new ArrayList<>();
    private AudioListAdapter mAudioListAdapter;

    private boolean isPlaying = false;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_choice_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
    }

    private void bindEvents() {
        rlPlay.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);
        ivAudioLeft.setOnFocusChangeListener(this);
        ivAudioRight.setOnFocusChangeListener(this);
        lvAudioList.setOnFocusChangeListener(this);
    }

    private void getData() {
        mLessonId = getIntent().getIntExtra(SystemConfig.LESSON, 1);
        loadBitmap();

        Map<String, Integer> map = new HashMap<>(1);
        map.put(LESSON_ID, 1);
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.AUDIO_LIST)
                .post(RequestBody.create(HttpConstant.JSON, json))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> initData());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseStr = response.body().string();
                    String data = (new JSONObject(responseStr)).optString("data");
                    mAudios = JSON.parseArray(data, Audio.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> initData());
                }
            }
        });
    }

    private void initData() {
        switch (mLessonId) {
            case 1:
                tvDifficult.setText(R.string.reception_last_semester);
                break;
            case 2:
                tvDifficult.setText(R.string.middle_last_semester);
                break;
            case 3:
                tvDifficult.setText(R.string.big_last_semester);
                break;
            case 4:
                tvDifficult.setText(R.string.reception_next_semester);
                break;
            case 5:
                tvDifficult.setText(R.string.middle_next_semester);
                break;
            case 6:
                tvDifficult.setText(R.string.big_next_semester);
                break;
            default:
                break;
        }
        lvAudioList.addHeaderView(getLayoutInflater().inflate(R.layout.audio_list_item, null));
        Collections.sort(mAudios, (audio1, audio2) -> {
            int i = audio1.getId() - audio2.getId();
            if (i == 0) {
                return audio1.getId() - audio2.getId();
            }
            return i;
        });
        mAudioListAdapter = new AudioListAdapter(getApplicationContext(), mAudios, mSongFouces);
        lvAudioList.setAdapter(mAudioListAdapter);
        lvAudioList.setItemsCanFocus(true);
        lvAudioList.setFocusable(false);

//        obtainViewFocus(rlPlay);
//        rlPlay.setFocusable(true);
//        rlPlay.setNextFocusLeftId(R.id.iv_audio_left);
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(AudioChoiceActivity.this, MainActivity.class));
    }

    @OnClick({R.id.rl_play, R.id.iv_play})
    public void onPlay() {
        if (isPlaying) {
            pauseMusicState();
            mMediaPlayer.pause();
        } else {
            if (currentPosition == -1 && !mAudios.isEmpty()) {
                currentPosition = 0;
                String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mAudios.get(currentPosition).getAudiofile())).toString();
                playingMusicState(currentPosition);
                playMusic(songUrl, currentPosition);
                return;
            }
            playingMusicState(currentPosition);
            mMediaPlayer.start();
        }
    }

    @OnClick(R.id.iv_audio_left)
    public void onPageLeft() {
//        if (currentPosition == 0 || currentPosition == -1) {
//            currentPosition = mAudios.size();
//        }
//
//        currentPosition = currentPosition - 1;
//        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mAudios.get(currentPosition).getAudiofile())).toString();
//        playMusic(songUrl, currentPosition);
//        playingMusicState(currentPosition);
    }

    @OnClick(R.id.iv_audio_right)
    public void onPageRight() {
//        if (currentPosition == mAudios.size() - 1) {
//            currentPosition = 0;
//        }
//
//        currentPosition = currentPosition + 1;
//        String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mAudios.get(currentPosition).getAudiofile())).toString();
//        playMusic(songUrl, currentPosition);
//        playingMusicState(currentPosition);
    }

    private void pauseMusicState() {
        isPlaying = false;
        ivPlay.setImageResource(R.drawable.audio_play_selector);
        tvPlay.setText(R.string.play);
        mAudioListAdapter.setPlayingIndex(-1);
    }

    private void playingMusicState(int position) {
        isPlaying = true;
        ivPlay.setImageResource(R.drawable.audio_pause_selector);
        tvPlay.setText(R.string.pause);
        mAudioListAdapter.setPlayingIndex(position);
        lvAudioList.post(() -> lvAudioList.smoothScrollToPosition(position + 1) );
    }

    private void playMusic(String songUrl, final int position) {
        showLoading(true);
        new PlayMusicAsyncTask(position).execute(songUrl);
    }

    private void play(int position) {
        try {
            showLoading(true);
            if (position == mAudios.size()) {
                position = 0;
            }

            currentPosition = position + 1;
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mAudios.get(currentPosition).getAudiofile())).toString();
            playMusic(songUrl, currentPosition);
            playingMusicState(currentPosition);
        } catch (Exception e) {

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_homepage:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlHomePage.setNextFocusDownId(R.id.rl_play);
                } else {
                    loseViewFocus(rlHomePage);
                }
                break;
            case R.id.rl_play:
                if (b) {
                    obtainViewFocus(rlPlay);
                    if (isPlaying) {
                        ivPlay.setImageResource(R.mipmap.audio_pause_pressed);
                    } else {
                        ivPlay.setImageResource(R.mipmap.audio_play_pressed);
                    }
                    tvPlay.setTextColor(ContextCompat.getColor(AudioChoiceActivity.this, R.color.playing_text));
                    rlPlay.setNextFocusLeftId(R.id.lv_audio_list);
                    rlPlay.setNextFocusDownId(R.id.lv_audio_list);
                } else {
                    loseViewFocus(rlPlay);
                    if (isPlaying) {
                        ivPlay.setImageResource(R.mipmap.audio_pause_def);
                    } else {
                        ivPlay.setImageResource(R.mipmap.audio_play_def);
                    }
                    tvPlay.setTextColor(ContextCompat.getColor(AudioChoiceActivity.this, R.color.white));
                }
                break;
            case R.id.iv_audio_left:
                if (b) {
                    ivAudioLeft.setImageResource(R.mipmap.audio_left_pressed);
                    obtainViewFocus(ivAudioLeft);
                    ivAudioLeft.setNextFocusUpId(R.id.rl_play);
                    ivAudioLeft.setNextFocusRightId(R.id.iv_audio_right);
                } else {
                    ivAudioLeft.setImageResource(R.mipmap.audio_left_def);
                    loseViewFocus(ivAudioLeft);
                }
                break;
            case R.id.iv_audio_right:
                if (b) {
                    ivAudioRight.setImageResource(R.mipmap.audio_right_pressed);
                    obtainViewFocus(ivAudioRight);
                    ivAudioRight.setNextFocusLeftId(R.id.iv_audio_left);
                } else {
                    ivAudioRight.setImageResource(R.mipmap.audio_right_def);
                    loseViewFocus(ivAudioRight);
                }
                break;
                default:
                    break;
        }
    }

    private boolean isFirst = true;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    getFocusRlPlay();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    getFocusRlPlay();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    getFocusRlPlay();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    getFocusRlPlay();
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

    private void getFocusRlPlay() {
        obtainViewFocus(rlPlay);
        rlPlay.setNextFocusLeftId(R.id.iv_audio_left);
        isFirst = false;
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

    private void loadBitmap() {
        switch (mLessonId) {
            case 1:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_1, R.mipmap.vedio_choice));
                break;
            case 2:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_2, R.mipmap.vedio_choice));
                break;
            case 3:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_3, R.mipmap.vedio_choice));
                break;
            case 4:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_4, R.mipmap.vedio_choice));
                break;
            case 5:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_5, R.mipmap.vedio_choice));
                break;
            case 6:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_6, R.mipmap.vedio_choice));
                break;
            default:
                ivDifficult.setImageBitmap(loadBitmap(R.mipmap.timg_1, R.mipmap.vedio_choice));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private AudioListAdapter.ISongFouces mSongFouces = position ->  {
        if (pbLoading.getVisibility() != View.VISIBLE) {
            currentPosition = position;
            String songUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mAudios.get(position).getAudiofile())).toString();
            playMusic(songUrl, currentPosition);
            playingMusicState(currentPosition);
            lvAudioList.setItemsCanFocus(true);
            lvAudioList.setFocusable(false);
        }
    };

    public class PlayMusicAsyncTask extends AsyncTask<String, String, Boolean> {

        private int position;

        public PlayMusicAsyncTask(int position) {
            this.position = position;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if (mMediaPlayer == null){
                mMediaPlayer = new MediaPlayer();
            }
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setDataSource(getApplicationContext(), Uri.parse(strings[0]));
                mMediaPlayer.prepare();
            } catch (Exception e) {
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(AudioChoiceActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
                });
            }
            mMediaPlayer.setOnPreparedListener(mediaPlayer -> {
                runOnUiThread(() -> {
                    showLoading(false);
                    mediaPlayer.start();
                });
            });
            mMediaPlayer.setOnCompletionListener(mediaPlayer -> {
                        runOnUiThread(() ->  play(position));
                    });
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showLoading(false);
        }
    }
}
