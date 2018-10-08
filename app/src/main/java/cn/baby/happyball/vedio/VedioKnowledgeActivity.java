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

public class VedioKnowledgeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    @BindView(R.id.iv_knowledge_answer)
    ImageView ivKnowledgeAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_knowledge);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        startActivity(new Intent(VedioKnowledgeActivity.this, VedioFinishActivity.class));
    }

    @OnClick(R.id.iv_homepage)
    public void onHomePage(){
        startActivity(new Intent(VedioKnowledgeActivity.this, MainActivity.class));
    }

    @OnClick(R.id.iv_knowledge_answer)
    public void onAnswer() {

    }
}
