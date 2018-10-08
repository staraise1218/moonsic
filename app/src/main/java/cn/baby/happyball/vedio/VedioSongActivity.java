package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;

public class VedioSongActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.iv_song_accompaniment)
    ImageView ivSongAccompaniment;
    @BindView(R.id.iv_song_sing)
    ImageView ivSongSing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_song);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        startActivity(new Intent(VedioSongActivity.this, VedioFinishActivity.class));
    }

    @OnClick(R.id.iv_homepage)
    public void onHomepage() {
        startActivity(new Intent(VedioSongActivity.this, MainActivity.class));
    }

    @OnClick(R.id.iv_song_accompaniment)
    public void onSongAccompaniment() {

    }

    @OnClick(R.id.iv_song_sing)
    public void onSongSing() {

    }
}
