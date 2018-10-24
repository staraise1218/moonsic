package cn.baby.happyball.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.VedioChoiceActiviy;
import cn.baby.happyball.vedio.VedioFinishActivity;

/**
 * @author DRH
 */
public class AudioPlayActivity extends BaseActivity {

    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomaPage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_play_number)
    TextView tvPlayNumber;
    @BindView(R.id.pb_play)
    ProgressBar pbPlay;
    @BindView(R.id.tv_play_time)
    TextView tvPlayTime;
    @BindView(R.id.vv_play)
    VideoView videoPlay;

    private Episode mEpisode;
    private Semester mSemester;
    private Lesson mLesson;
    private String mKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        ButterKnife.bind(this);
        getData();
        initData();
    }

    public void onPlay() {
        setValue(mKey, getString(R.string.played));
        startActivity(new Intent(AudioPlayActivity.this, AudioChoiceActiviy.class));
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        setValue(mKey, String.valueOf(videoPlay.getCurrentPosition()));
        startActivity(new Intent(AudioPlayActivity.this, AudioChoiceActiviy.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        setValue(mKey, String.valueOf(videoPlay.getCurrentPosition()));
        startActivity(new Intent(AudioPlayActivity.this, MainActivity.class));
    }

    private void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
        mSemester = (Semester) getIntent().getSerializableExtra(SystemConfig.SEMESTER);
        mLesson = (Lesson) getIntent().getSerializableExtra(SystemConfig.LESSON);
        StringBuilder builder = new StringBuilder();
        builder.append(SystemConfig.EPISODE_TIME).append(mSemester.getId()).append(mLesson.getId());
        mKey = builder.toString();
    }

    private void initData() {
        String videoUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mEpisode.getVideofile())).toString();
        videoPlay.setVideoPath(videoUrl);
        videoPlay.setMediaController(new MediaController(this));
        videoPlay.setOnPreparedListener(mp -> videoPlay.start());
        videoPlay.setOnCompletionListener(mp -> videoPlay.start());
        videoPlay.setOnCompletionListener(mediaPlayer -> onPlay());
        videoPlay.requestFocus();
    }
}
