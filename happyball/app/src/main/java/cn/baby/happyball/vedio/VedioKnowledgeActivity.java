package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.bean.Knowledge;
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
    @BindView(R.id.rl_answer)
    RelativeLayout rlAnswer;
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
        setContentView(R.layout.vedio_knowledge_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
    }

    private void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);
        rlAnswer.setOnFocusChangeListener(this);

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
                try {
                    final String responseStr = response.body().string();
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
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        ivKnowledgeSecond.setVisibility(View.GONE);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 2:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 3:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString(), ivKnowledgeThird);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        tvKnowledgeFirst.setVisibility(View.GONE);
                        tvKnowledgeSecond.setVisibility(View.GONE);
                        tvKnowledgeThird.setVisibility(View.GONE);
                        tvKnowledgeFour.setVisibility(View.GONE);
                        break;
                    case 4:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString(), ivKnowledgeThird);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(3))).toString(), ivKnowledgeFour);
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
                            obtainViewFocus(rlKnowledgeFirst);
                            tvKnowledgeFirst.setVisibility(View.VISIBLE);
                            tvKnowledgeFirst.setText(s);
                            break;
                        case 2:
                            obtainViewFocus(rlKnowledgeSecond);
                            tvKnowledgeSecond.setVisibility(View.VISIBLE);
                            tvKnowledgeSecond.setText(s);
                            break;
                        case 3:
                            obtainViewFocus(rlKnowledgeThird);
                            tvKnowledgeThird.setVisibility(View.VISIBLE);
                            tvKnowledgeThird.setText(s);
                            break;
                        case 4:
                            obtainViewFocus(rlKnowledgeFour);
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
        switch (view.getId()) {
            case R.id.rl_homepage:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlHomePage.requestLayout();
                    rlHomePage.setFocusable(true);
                    rlHomePage.setNextFocusDownId(R.id.rl_answer);
                    rlHomePage.setNextFocusLeftId(R.id.rl_knowledge_first);
                } else {
                    loseViewFocus(rlHomePage);
                }
            case R.id.rl_answer:
                if (b) {
                    obtainViewFocus(rlAnswer);
                    rlAnswer.requestLayout();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusUpId(R.id.rl_homepage);
                } else {
                    loseViewFocus(rlAnswer);
                }
                break;
            case R.id.rl_knowledge_first:
                if (b) {
                    obtainViewFocus(rlKnowledgeFirst);
                    rlKnowledgeFirst.requestLayout();
                    rlKnowledgeFirst.setFocusable(true);
                    rlKnowledgeFirst.setNextFocusUpId(R.id.rl_homepage);
                    rlKnowledgeFirst.setNextFocusRightId(R.id.rl_knowledge_second);
                } else {
                    loseViewFocus(rlKnowledgeFirst);
                }
                break;
            case R.id.rl_knowledge_second:
                if (b) {
                    obtainViewFocus(rlKnowledgeSecond);
                    rlKnowledgeSecond.requestLayout();
                    rlKnowledgeSecond.setFocusable(true);
                    rlKnowledgeSecond.setNextFocusLeftId(R.id.rl_knowledge_first);
                    rlKnowledgeSecond.setNextFocusRightId(R.id.rl_knowledge_third);
                } else {
                    loseViewFocus(rlKnowledgeSecond);
                }
                break;
            case R.id.rl_knowledge_third:
                if (b) {
                    obtainViewFocus(rlKnowledgeThird);
                    rlKnowledgeThird.requestLayout();
                    rlKnowledgeThird.setFocusable(true);
                    rlKnowledgeSecond.setNextFocusLeftId(R.id.rl_knowledge_second);
                    rlKnowledgeSecond.setNextFocusRightId(R.id.rl_knowledge_four);
                } else {
                    loseViewFocus(rlKnowledgeThird);
                }
                break;
            case R.id.rl_knowledge_four:
                if (b) {
                    obtainViewFocus(rlKnowledgeFour);
                    rlKnowledgeFour.requestLayout();
                    rlKnowledgeFour.setFocusable(true);
                    rlKnowledgeFour.setNextFocusLeftId(R.id.rl_knowledge_third);
                    rlKnowledgeFour.setNextFocusRightId(R.id.rl_answer);
                } else {
                    loseViewFocus(rlKnowledgeFour);
                }
                break;
            default:
                break;
        }
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    boolean isFirst = true;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlAnswer);
                    rlAnswer.requestFocus();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlAnswer);
                    rlAnswer.requestFocus();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlAnswer);
                    rlAnswer.requestFocus();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlAnswer);
                    rlAnswer.requestFocus();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_BACK:
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    onBackPressed();
                }
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
