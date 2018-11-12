package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.bean.Knowledge;
import cn.baby.happyball.bean.SingleDance;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VedioKnowledgeActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final String EPISODE_ID = "episode_id";
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

    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Episode mEpisode;
    private Knowledge mKnowledge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_knowledge);
        ButterKnife.bind(this);
        bindEvents();
        getData();
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
        showLoading(true);
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);

        Map<String, Integer> map = new HashMap<>(1);
        map.put(EPISODE_ID, Integer.valueOf(mEpisode.getEpisode()));
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.VIDEO_QUESTION)
                .post(RequestBody.create(HttpConstant.JSON, json))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> initData());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                try {
                    String data = (new JSONObject(responseStr)).optString("data");
                    mKnowledge = JSON.parseObject(data, Knowledge.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> initData());
                }
            }
        });
    }

    private void initData() {
        if (mKnowledge != null) {
            tvKnowledgeQuestion.setText(getString(R.string.knowledge_question, mKnowledge.getTitle()));
            if (!mKnowledge.getImages().isEmpty()) {
                switch (mKnowledge.getImages().size()) {
                    case 1:
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString())
                                .into(ivKnowledgeFirst);
                        ivKnowledgeSecond.setVisibility(View.GONE);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 2:
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString())
                                .into(ivKnowledgeFirst);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString())
                                .into(ivKnowledgeSecond);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 3:
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString())
                                .into(ivKnowledgeFirst);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString())
                                .into(ivKnowledgeSecond);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString())
                                .into(ivKnowledgeThird);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 4:
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString())
                                .into(ivKnowledgeFirst);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString())
                                .into(ivKnowledgeSecond);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString())
                                .into(ivKnowledgeThird);
                        Picasso.with(getApplicationContext())
                                .load((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(3))).toString())
                                .into(ivKnowledgeFour);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    default:
                        ivKnowledgeFirst.setVisibility(View.GONE);
                        ivKnowledgeSecond.setVisibility(View.GONE);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                }
            }
        }
        showLoading(false);
    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioKnowledgeActivity.this, VedioFinishActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomePage() {
        startActivity(new Intent(VedioKnowledgeActivity.this, MainActivity.class));
    }

    @OnClick(R.id.iv_answer)
    public void onAnswer() {
        if (mKnowledge != null) {
            if (!mKnowledge.getAnswer().isEmpty()) {
                for (String s : mKnowledge.getAnswer()) {
                    switch (Integer.valueOf(s)) {
                        case 1:
                            tvKnowledgeFirst.setVisibility(View.VISIBLE);
                            tvKnowledgeFirst.setText(s);
                            break;
                        case 2:
                            tvKnowledgeSecond.setVisibility(View.VISIBLE);
                            tvKnowledgeSecond.setText(s);
                            break;
                        case 3:
                            tvKnowledgeThird.setVisibility(View.VISIBLE);
                            tvKnowledgeThird.setText(s);
                            break;
                        case 4:
                            tvKnowledgeFour.setVisibility(View.VISIBLE);
                            tvKnowledgeFour.setText(s);
                            break;
                        default:
                            ivKnowledgeFirst.setVisibility(View.GONE);
                            ivKnowledgeSecond.setVisibility(View.GONE);
                            ivKnowledgeThird.setVisibility(View.GONE);
                            ivKnowledgeFour.setVisibility(View.GONE);
                            tvKnowledgeFirst.setVisibility(View.GONE);
                            tvKnowledgeSecond.setVisibility(View.GONE);
                            tvKnowledgeThird.setVisibility(View.GONE);
                            tvKnowledgeFour.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        }
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

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
