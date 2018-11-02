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
        obtainViewFocus(llReplay);
        llReplay.requestFocus();
        llReplay.setFocusable(true);
        llReplay.setNextFocusLeftId(R.id.rl_study);
        llReplay.setNextFocusRightId(R.id.rl_knowledge);
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
        switch (view.getId()) {
            case R.id.rl_back:
                if (b) {
                    obtainViewFocus(rlBack);
                    rlBack.setNextFocusRightId(R.id.rl_homepage);
                    rlBack.setNextFocusDownId(R.id.rl_study);
                } else {
                    loseViewFocus(rlBack);
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlHomePage.setNextFocusLeftId(R.id.rl_back);
                    rlHomePage.setNextFocusDownId(R.id.rl_knowledge);
                } else {
                    loseViewFocus(rlHomePage);
                }
                break;
            case R.id.ll_replay:
                if (b) {
                    obtainViewFocus(llReplay);
                    llReplay.setNextFocusLeftId(R.id.rl_study);
                    llReplay.setNextFocusRightId(R.id.rl_knowledge);
                } else {
                    loseViewFocus(llReplay);
                }
                break;
            case R.id.rl_study:
                if (b) {
                    obtainViewFocus(rlStudy);
                    rlStudy.setNextFocusUpId(R.id.rl_back);
                    rlStudy.setNextFocusRightId(R.id.ll_replay);
                } else {
                    loseViewFocus(rlStudy);
                }
                break;
            case R.id.rl_knowledge:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlKnowledge.setNextFocusLeftId(R.id.ll_replay);
                    rlKnowledge.setNextFocusUpId(R.id.rl_homepage);
                } else {
                    loseViewFocus(rlHomePage);
                }
                break;
            default:
                if (b) {
                    obtainViewFocus(view);
                } else {
                    loseViewFocus(view);
                }
                break;
        }
    }

    boolean isFirst = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isFirst) {
                    obtainViewFocus(llReplay);
                    llReplay.requestFocus();
                    llReplay.setFocusable(true);
                    llReplay.setNextFocusLeftId(R.id.rl_study);
                    llReplay.setNextFocusRightId(R.id.rl_knowledge);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
