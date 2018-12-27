package cn.baby.happyball.audio;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
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
    private String[] mMusics = new String[]{"pay_attention_to_thunder_and_rain.mp3", "don_t_talk_to_strangers.mp3"};

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
//        rlPlay.setOnFocusChangeListener(this);
//        ivAudioLeft.setOnFocusChangeListener(this);
//        ivAudioRight.setOnFocusChangeListener(this);
    }

    private void getData() {
        mLessonId = getIntent().getIntExtra(SystemConfig.LESSON, 1);
        loadBitmap();

        Audio audio = new Audio();
        audio.setId(1);
        audio.setTitle("打雷下雨要注意");
        audio.setSinger("某某");
        audio.setAlbum("打雷下雨要注意");
        audio.setTimelong("01:39");
        mAudios.add(audio);

        Audio audio1 = new Audio();
        audio1.setId(2);
        audio1.setTitle("不要跟陌生人说话");
        audio1.setSinger("某某");
        audio1.setAlbum("不要跟陌生人说话");
        audio1.setTimelong("02:48");
        mAudios.add(audio1);

        Audio audio2 = new Audio();
        audio2.setId(3);
        audio2.setTitle("打雷下雨要注意");
        audio2.setSinger("某某");
        audio2.setAlbum("打雷下雨要注意");
        audio2.setTimelong("01:39");
        mAudios.add(audio2);

        Audio audio3 = new Audio();
        audio3.setId(4);
        audio3.setTitle("不要跟陌生人说话");
        audio3.setSinger("某某");
        audio3.setAlbum("不要跟陌生人说话");
        audio3.setTimelong("02:48");
        mAudios.add(audio3);

        initData();

//        Map<String, Integer> map = new HashMap<>(1);
//        map.put(LESSON_ID, 1);
//        String json = JSON.toJSONString(map);
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(HttpConstant.URL + HttpConstant.AUDIO_LIST)
//                .post(RequestBody.create(HttpConstant.JSON, json))
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread(() -> initData());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    final String responseStr = response.body().string();
//                    String data = (new JSONObject(responseStr)).optString("data");
//                    mAudios = JSON.parseArray(data, Audio.class);
//                    runOnUiThread(() -> initData());
//                } catch (Exception e) {
//                    runOnUiThread(() -> initData());
//                }
//            }
//        });
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
        currentPosition = 0;
        mAudioListAdapter.setPlayingIndex(currentPosition);
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
                playingMusicState(currentPosition);
                playMusic(currentPosition);
                return;
            }
            playingMusicState(currentPosition);
            mMediaPlayer.start();
        }
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
        lvAudioList.post(() -> lvAudioList.smoothScrollToPosition(position + 1));
    }

    private void playMusic(final int position) {
        String songUrl = "";
        if (position % 2 == 0) {
            songUrl = mMusics[0];
        } else {
            songUrl = mMusics[1];
        }
        mAudioListAdapter.setPlaying(true);
        new PlayMusicAsyncTask(position).execute(songUrl);
    }

    private void play(int position) {
        try {
            showLoading(true);
            if (position == mAudios.size()) {
                position = 0;
            }

            currentPosition = position + 1;
            playMusic(currentPosition);
            playingMusicState(currentPosition);
        } catch (Exception e) {

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_play:
                if (b) {
                    if (isPlaying) {
                        ivPlay.setImageResource(R.mipmap.audio_pause_pressed);
                    } else {
                        ivPlay.setImageResource(R.mipmap.audio_play_pressed);
                    }
                    tvPlay.setTextColor(ContextCompat.getColor(AudioChoiceActivity.this, R.color.playing_text));
                    rlPlay.setNextFocusLeftId(R.id.lv_audio_list);
                    rlPlay.setNextFocusDownId(R.id.lv_audio_list);
                } else {
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
                    ivAudioLeft.setNextFocusUpId(R.id.rl_play);
                    ivAudioLeft.setNextFocusRightId(R.id.iv_audio_right);
                } else {
                    ivAudioLeft.setImageResource(R.mipmap.audio_left_def);
                }
                break;
            case R.id.iv_audio_right:
                if (b) {
                    ivAudioRight.setImageResource(R.mipmap.audio_right_pressed);
                    ivAudioRight.setNextFocusLeftId(R.id.iv_audio_left);
                } else {
                    ivAudioRight.setImageResource(R.mipmap.audio_right_def);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (currentPosition == mAudios.size() - 1) {
                        currentPosition = -1;
                    }

                    seek = 0;
                    currentPosition = currentPosition + 1;
                    playMusic(currentPosition);
                    playingMusicState(currentPosition);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (currentPosition == 0 || currentPosition == -1) {
                        currentPosition = mAudios.size();
                    }

                    seek = 0;
                    currentPosition = currentPosition - 1;
                    playMusic(currentPosition);
                    playingMusicState(currentPosition);
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
            case 4:
                ivDifficult.setImageResource(R.mipmap.main_reception);
                break;
            case 2:
            case 5:
                ivDifficult.setImageResource(R.mipmap.main_middle);
                break;
            case 3:
            case 6:
                ivDifficult.setImageResource(R.mipmap.main_big);
                break;
            default:
                ivDifficult.setImageResource(R.mipmap.main_reception);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private AudioListAdapter.ISongFouces mSongFouces = new AudioListAdapter.ISongFouces() {
        @Override
        public void onPlaying(int position) {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    seek = mMediaPlayer.getCurrentPosition();
                    mAudioListAdapter.setPlaying(false);
                    mMediaPlayer.pause();
                } else {
                    playMusic(currentPosition);
                    mAudioListAdapter.setPlaying(true);
                }
            } else {
                currentPosition = position;
                lvAudioList.setItemsCanFocus(true);
                lvAudioList.setFocusable(false);
                playMusic(currentPosition);
                playingMusicState(currentPosition);
            }
        }
    };

    private int seek;

    public class PlayMusicAsyncTask extends AsyncTask<String, String, Boolean> {

        private int position;

        public PlayMusicAsyncTask(int position) {
            this.position = position;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if (mMediaPlayer == null) {
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
                    Toast.makeText(AudioChoiceActivity.this, "音频加载失败", Toast.LENGTH_SHORT).show();
                });
            }
            mMediaPlayer.setOnPreparedListener(mediaPlayer ->
                    runOnUiThread(() -> {
                        showLoading(false);
                        mMediaPlayer.seekTo(seek);
                        mediaPlayer.start();
                    })
            );
            mMediaPlayer.setOnCompletionListener(mediaPlayer -> runOnUiThread(() -> play(position)));
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showLoading(false);
        }
    }
}
