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

public class VedioDanceActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.iv_dance_boom)
    ImageView ivDanceBoom;
    @BindView(R.id.iv_dance_sky)
    ImageView ivDanceSky;
    @BindView(R.id.iv_dance_dark)
    ImageView ivDanceDark;
    @BindView(R.id.iv_dance_clouds)
    ImageView ivDanceClouds;
    @BindView(R.id.iv_dance_safe)
    ImageView ivDanceSafe;
    @BindView(R.id.iv_dance_ipad)
    ImageView ivDanceIpad;
    @BindView(R.id.iv_dance_computer)
    ImageView ivDanceComputer;
    @BindView(R.id.iv_dance_thunder)
    ImageView ivDanceThumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_dance);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        startActivity(new Intent(VedioDanceActivity.this, VedioFinishActivity.class));
    }

    @OnClick(R.id.iv_homepage)
    public void onHomepage() {
        startActivity(new Intent(VedioDanceActivity.this, MainActivity.class));
    }
}
