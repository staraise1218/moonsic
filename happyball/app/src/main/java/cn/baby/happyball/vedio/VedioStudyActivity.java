package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.constant.SystemConfig;

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

    private Episode mEpisode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_study);
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
//        obtainViewFocus(rlSong);
//        rlSong.requestFocus();
//        rlSong.setFocusable(true);
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
        if (b) {
            obtainViewFocus(view);
        } else {
            loseViewFocus(view);
        }
    }
}
