package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;

public class VedioSongActivity extends BaseActivity {

    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomaPage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_song_accompaniment)
    RelativeLayout rlSongAccompaniment;
    @BindView(R.id.iv_song_accompaniment)
    ImageView ivSongAccompaniment;
    @BindView(R.id.rl_song_sing)
    RelativeLayout rlSongSing;
    @BindView(R.id.iv_song_sing)
    ImageView ivSongSing;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_song);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioSongActivity.this, VedioFinishActivity.class));
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
}
