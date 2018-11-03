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

public class VedioKnowledgeActivity extends BaseActivity implements View.OnFocusChangeListener {

    /**
     * 返回
     */
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;

    @BindView(R.id.tv_knowledge_title)
    TextView tvKnowledgeTitle;
    @BindView(R.id.tv_knowledge_question)
    TextView tvKnowledgeQuestion;

    @BindView(R.id.rl_knowledge_first)
    RelativeLayout rlKnowledgeFirst;
    @BindView(R.id.iv_knowledge_first)
    ImageView ivKnowledgeFirst;
    @BindView(R.id.tv_knowledge_first)
    TextView tvKnowledgeFirst;

    @BindView(R.id.rl_knowledge_second)
    RelativeLayout rlKnowledgeSecond;
    @BindView(R.id.iv_knowledge_second)
    ImageView ivKnowledgeSecond;
    @BindView(R.id.tv_knowledge_second)
    TextView tvKnowledgeSecond;

    @BindView(R.id.rl_knowledge_third)
    RelativeLayout rlKnowledgeThird;
    @BindView(R.id.iv_knowledge_third)
    ImageView ivKnowledgeThird;
    @BindView(R.id.tv_knowledge_third)
    TextView tvKnowledgeThird;

    @BindView(R.id.rl_knowledge_four)
    RelativeLayout rlKnowledgeFour;
    @BindView(R.id.iv_knowledge_four)
    ImageView ivKnowledgeFour;
    @BindView(R.id.tv_knowledge_four)
    TextView tvKnowledgeFour;

    @BindView(R.id.iv_answer)
    ImageView ivKnowledgeAnswer;

    private Episode mEpisode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_knowledge);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    private void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);

        rlKnowledgeFirst.setOnFocusChangeListener(this);
        rlKnowledgeSecond.setOnFocusChangeListener(this);
        rlKnowledgeThird.setOnFocusChangeListener(this);
        rlKnowledgeFour.setOnFocusChangeListener(this);
    }

    private void getData() {

    }

    private void initData() {

    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioKnowledgeActivity.this, VedioFinishActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomePage(){
        startActivity(new Intent(VedioKnowledgeActivity.this, MainActivity.class));
    }

    @OnClick(R.id.iv_answer)
    public void onAnswer() {

    }

    @OnClick({R.id.rl_knowledge_first, R.id.iv_knowledge_first})
    public void onKnowledgeFirst() {

    }

    @OnClick({R.id.rl_knowledge_second, R.id.iv_knowledge_second})
    public void onKnowledgeSecond() {

    }

    @OnClick({R.id.rl_knowledge_third, R.id.iv_knowledge_third})
    public void onKnowledgeThird() {

    }

    @OnClick({R.id.rl_knowledge_four, R.id.iv_knowledge_four})
    public void onKnowledgeFour() {

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
