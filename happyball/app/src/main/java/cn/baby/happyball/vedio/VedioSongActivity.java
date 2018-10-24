package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.constant.SystemConfig;

public class VedioSongActivity extends BaseActivity implements View.OnFocusChangeListener {

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

    private Episode mEpisode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_song);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    public void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomaPage.setOnFocusChangeListener(this);
        rlSongAccompaniment.setOnFocusChangeListener(this);
        rlSongSing.setOnFocusChangeListener(this);
    }

    public void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    public void initData() {
//        obtainViewFocus(rlSongSing);
//        rlSongSing.requestFocus();
//        rlSongSing.setFocusable(true);
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

    }

    @OnClick({R.id.iv_song_sing, R.id.rl_song_sing})
    public void onSongSing() {

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
