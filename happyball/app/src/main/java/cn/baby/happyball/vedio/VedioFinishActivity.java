package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;

public class VedioFinishActivity extends BaseActivity {

    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_replay)
    LinearLayout llReplay;
    @BindView(R.id.rl_song)
    RelativeLayout rlSong;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.rl_dance)
    RelativeLayout rlDance;
    @BindView(R.id.iv_dance)
    ImageView ivDance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_finish);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_song, R.id.rl_song})
    public void onSong() {
        startActivity(new Intent(VedioFinishActivity.this, VedioSongActivity.class));
    }

    @OnClick({R.id.iv_dance, R.id.rl_dance})
    public void onDance() {
        startActivity(new Intent(VedioFinishActivity.this, VedioDanceActivity.class));
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioFinishActivity.this, VedioPlayActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioFinishActivity.this, MainActivity.class));
    }
}
