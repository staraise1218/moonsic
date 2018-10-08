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

public class VedioDanceActivity extends BaseActivity {

    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_dance_first)
    RelativeLayout rlDanceFirst;
    @BindView(R.id.iv_dance_first)
    ImageView ivDanceFirst;
    @BindView(R.id.tv_dance_first)
    TextView tvDanceFirst;
    @BindView(R.id.rl_dance_second)
    RelativeLayout rlDanceSecond;
    @BindView(R.id.iv_dance_second)
    ImageView ivDanceSecond;
    @BindView(R.id.tv_dance_second)
    TextView tvDanceSecond;
    @BindView(R.id.rl_dance_third)
    RelativeLayout rlDanceThird;
    @BindView(R.id.iv_dance_third)
    ImageView ivDanceThird;
    @BindView(R.id.tv_dance_third)
    TextView tvDanceThird;
    @BindView(R.id.rl_dance_four)
    RelativeLayout rlDanceFour;
    @BindView(R.id.iv_dance_four)
    ImageView ivDanceFour;
    @BindView(R.id.tv_dance_four)
    TextView tvDanceFour;
    @BindView(R.id.rl_dance_five)
    RelativeLayout rlDanceFive;
    @BindView(R.id.iv_dance_five)
    ImageView ivDanceFive;
    @BindView(R.id.tv_dance_five)
    TextView tvDanceFive;
    @BindView(R.id.rl_dance_six)
    RelativeLayout rlDanceSix;
    @BindView(R.id.iv_dance_six)
    ImageView ivDanceSix;
    @BindView(R.id.tv_dance_six)
    TextView tvDanceSix;
    @BindView(R.id.rl_dance_seven)
    RelativeLayout rlDanceSeven;
    @BindView(R.id.iv_dance_seven)
    ImageView ivDanceSeven;
    @BindView(R.id.tv_dance_seven)
    TextView tvDanceSeven;
    @BindView(R.id.rl_dance_eight)
    RelativeLayout rlDanceEight;
    @BindView(R.id.iv_dance_eight)
    ImageView ivDanceEight;
    @BindView(R.id.tv_dance_eight)
    TextView tvDanceEight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_dance);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioDanceActivity.this, VedioFinishActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioDanceActivity.this, MainActivity.class));
    }
}
