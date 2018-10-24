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

/**
 * @author DRH
 */
public class VedioFinishActivity extends BaseActivity implements View.OnFocusChangeListener{

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
     * 重播
     */
    @BindView(R.id.ll_replay)
    LinearLayout llReplay;
    /**
     * 学习
     */
    @BindView(R.id.rl_study)
    RelativeLayout rlStudy;
    @BindView(R.id.iv_study)
    ImageView ivStudy;
    /**
     * 知识点
     */
    @BindView(R.id.rl_knowledge)
    RelativeLayout rlKnowledge;
    @BindView(R.id.iv_knowledge)
    ImageView ivKnowledge;

    private Episode mEpisode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_finish);
        ButterKnife.bind(this);
        bindEvnets();
        getData();
        initData();
    }

    private void bindEvnets() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);
        llReplay.setOnFocusChangeListener(this);
        rlStudy.setOnFocusChangeListener(this);
        rlKnowledge.setOnFocusChangeListener(this);
    }

    private void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);
    }

    private void initData() {
//        obtainViewFocus(rlStudy);
//        rlStudy.requestFocus();
//        rlStudy.setFocusable(true);
    }

    @OnClick(R.id.ll_replay)
    public void onReplay() {
        startActivity(new Intent(VedioFinishActivity.this, VedioPlayActivity.class));
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioFinishActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioFinishActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_study, R.id.rl_study})
    public void onSong() {
        startActivity(new Intent(VedioFinishActivity.this, VedioStudyActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @OnClick({R.id.iv_knowledge, R.id.rl_knowledge})
    public void onDance() {
        startActivity(new Intent(VedioFinishActivity.this, VedioKnowledgeActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
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
