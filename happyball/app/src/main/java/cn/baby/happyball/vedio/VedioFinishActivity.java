package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
public class VedioFinishActivity extends BaseActivity implements View.OnFocusChangeListener {

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

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Episode mEpisode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_finish_activity);
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
        // do nothing
    }

    @OnClick(R.id.ll_replay)
    public void onReplay() {
//        VedioPlayActivity.isReplay = true;
//        startActivity(new Intent(VedioFinishActivity.this, VedioPlayActivity.class)
//                .putExtra(SystemConfig.EPISODE, mEpisode));
        VedioFinishActivity.this.finish();
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioFinishActivity.this, VedioChoiceActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioFinishActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_study, R.id.rl_study})
    public void onStudy() {
        startActivity(new Intent(VedioFinishActivity.this, VedioStudyActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @OnClick({R.id.iv_knowledge, R.id.rl_knowledge})
    public void onKnowledge() {
        startActivity(new Intent(VedioFinishActivity.this, VedioKnowledgeActivity.class)
                .putExtra(SystemConfig.EPISODE, mEpisode));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_back:
                if (b) {
                    rlBack.setNextFocusRightId(R.id.rl_homepage);
                    rlBack.setNextFocusDownId(R.id.rl_study);
                } else {
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                    rlHomePage.setNextFocusLeftId(R.id.rl_back);
                    rlHomePage.setNextFocusDownId(R.id.rl_study);
                    rlHomePage.setNextFocusRightId(R.id.rl_study);
                } else {
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.ll_replay:
                if (b) {
                    llReplay.setBackgroundResource(R.mipmap.choice_episode_focus);
                    llReplay.setNextFocusLeftId(R.id.rl_study);
                    llReplay.setNextFocusRightId(R.id.rl_knowledge);
                } else {
                    llReplay.setBackgroundResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_study:
                if (b) {
                    ivStudy.setImageResource(R.mipmap.finish_study_focus);
                    rlStudy.setNextFocusUpId(R.id.rl_back);
                    rlStudy.setNextFocusRightId(R.id.ll_replay);
                } else {
                    ivStudy.setImageResource(R.mipmap.finish_study);
                }
                break;
            case R.id.rl_knowledge:
                if (b) {
                    ivKnowledge.setImageResource(R.mipmap.finish_knowledge_focus);
                    rlKnowledge.setNextFocusLeftId(R.id.ll_replay);
                    rlKnowledge.setNextFocusUpId(R.id.rl_homepage);
                } else {
                    ivKnowledge.setImageResource(R.mipmap.finish_knowledge);
                }
                break;
            default:
                break;
        }
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
