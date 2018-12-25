package cn.baby.happyball.vedio;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
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
    @BindView(R.id.iv_speak_question)
    ImageView ivSpeakQuestion;

    @BindView(R.id.rl_knowledge_first)
    RelativeLayout rlKnowledgeFirst;
    @BindView(R.id.iv_knowledge_first)
    ImageView ivKnowledgeFirst;

    @BindView(R.id.rl_knowledge_second)
    RelativeLayout rlKnowledgeSecond;
    @BindView(R.id.iv_knowledge_second)
    ImageView ivKnowledgeSecond;

    @BindView(R.id.rl_knowledge_third)
    RelativeLayout rlKnowledgeThird;
    @BindView(R.id.iv_knowledge_third)
    ImageView ivKnowledgeThird;

    @BindView(R.id.rl_knowledge_four)
    RelativeLayout rlKnowledgeFour;
    @BindView(R.id.iv_knowledge_four)
    ImageView ivKnowledgeFour;

    @BindView(R.id.iv_answer)
    ImageView ivKnowledgeAnswer;
    @BindView(R.id.rl_answer)
    RelativeLayout rlAnswer;
    /**
     * 加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.iv_knowledge_first_answer)
    ImageView ivKnowledgeFirstAnswer;
    @BindView(R.id.iv_knowledge_second_answer)
    ImageView ivKnowledgeSecondAnswer;
    @BindView(R.id.iv_knowledge_third_answer)
    ImageView ivKnowledgeThirdAnswer;
    @BindView(R.id.iv_knowledge_four_answer)
    ImageView ivKnowledgeFourAnswer;

    private Episode mEpisode;
    private Knowledge mKnowledge;
    private MediaPlayer mMediaPlayer;

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
        ivSpeakQuestion.setOnFocusChangeListener(this);

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
                        ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                        ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                        ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                        ivKnowledgeFourAnswer.setVisibility(View.GONE);
                        break;
                    case 2:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                        ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                        ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                        ivKnowledgeFourAnswer.setVisibility(View.GONE);
                        break;
                    case 3:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString(), ivKnowledgeThird);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                        ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                        ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                        ivKnowledgeFourAnswer.setVisibility(View.GONE);
                        break;
                    case 4:
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(0))).toString(), ivKnowledgeFirst);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(1))).toString(), ivKnowledgeSecond);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(2))).toString(), ivKnowledgeThird);
                        loadImage((new StringBuilder().append(HttpConstant.RES_URL).append(mKnowledge.getImages().get(3))).toString(), ivKnowledgeFour);
                        ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                        ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                        ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                        ivKnowledgeFourAnswer.setVisibility(View.GONE);
                        break;
                    default:
                        ivKnowledgeFirst.setVisibility(View.GONE);
                        ivKnowledgeSecond.setVisibility(View.GONE);
                        ivKnowledgeThird.setVisibility(View.GONE);
                        ivKnowledgeFour.setVisibility(View.GONE);
                        ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                        ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                        ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                        ivKnowledgeFourAnswer.setVisibility(View.GONE);
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

    @OnClick({R.id.iv_answer, R.id.rl_answer})
    public void onAnswer() {
        try {
            //播放 assets 音乐文件
            AssetFileDescriptor fd = getAssets().openFd("biubiubiu.mp3");
            if (mMediaPlayer != null) {
                mMediaPlayer.pause();
                mMediaPlayer.reset();
            } else {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 测试代码
        List<String> answers = new ArrayList<>(3);
        answers.add("1");
        answers.add("2");
        answers.add("3");
        for (String s : answers) {
            switch (Integer.valueOf(s)) {
                case 1:
                    obtainViewFocus(rlKnowledgeFirst);
                    ivKnowledgeFirstAnswer.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    obtainViewFocus(rlKnowledgeSecond);
                    ivKnowledgeSecondAnswer.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    obtainViewFocus(rlKnowledgeThird);
                    ivKnowledgeThirdAnswer.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    obtainViewFocus(rlKnowledgeFour);
                    ivKnowledgeFourAnswer.setVisibility(View.VISIBLE);
                    break;
                default:
                    ivKnowledgeFirst.setVisibility(View.GONE);
                    ivKnowledgeSecond.setVisibility(View.GONE);
                    ivKnowledgeThird.setVisibility(View.GONE);
                    ivKnowledgeFour.setVisibility(View.GONE);
                    ivKnowledgeFirstAnswer.setVisibility(View.GONE);
                    ivKnowledgeSecondAnswer.setVisibility(View.GONE);
                    ivKnowledgeThirdAnswer.setVisibility(View.GONE);
                    ivKnowledgeFourAnswer.setVisibility(View.GONE);
                    break;
            }
        }

//        if (mKnowledge != null) {
//            if (!mKnowledge.getAnswer().isEmpty()) {
//                for (String s : answers) {
//                    switch (Integer.valueOf(s)) {
//                        case 1:
//                            obtainViewFocus(rlKnowledgeFirst);
//                            ivKnowledgeFirstAnswer.setVisibility(View.VISIBLE);
//                            break;
//                        case 2:
//                            obtainViewFocus(rlKnowledgeSecond);
//                            ivKnowledgeSecondAnswer.setVisibility(View.VISIBLE);
//                            break;
//                        case 3:
//                            obtainViewFocus(rlKnowledgeThird);
//                            ivKnowledgeThirdAnswer.setVisibility(View.VISIBLE);
//                            break;
//                        case 4:
//                            obtainViewFocus(rlKnowledgeFour);
//                            ivKnowledgeFourAnswer.setVisibility(View.VISIBLE);
//                            break;
//                        default:
//                            ivKnowledgeFirst.setVisibility(View.GONE);
//                            ivKnowledgeSecond.setVisibility(View.GONE);
//                            ivKnowledgeThird.setVisibility(View.GONE);
//                            ivKnowledgeFour.setVisibility(View.GONE);
//                            ivKnowledgeFirstAnswer.setVisibility(View.GONE);
//                            ivKnowledgeSecondAnswer.setVisibility(View.GONE);
//                            ivKnowledgeThirdAnswer.setVisibility(View.GONE);
//                            ivKnowledgeFourAnswer.setVisibility(View.GONE);
//                            break;
//                    }
//                }
//            }
//        }
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
//                    obtainViewFocus(rlHomePage);
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                    rlHomePage.requestLayout();
                    rlHomePage.setFocusable(true);
                    rlHomePage.setNextFocusDownId(R.id.rl_answer);
                    rlHomePage.setNextFocusLeftId(R.id.iv_speak_question);
                } else {
//                    loseViewFocus(rlHomePage);
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
            case R.id.iv_speak_question:
                if (b) {
                    ivSpeakQuestion.setImageResource(R.mipmap.song_playing_pressed);
//                    obtainViewFocus(ivSpeakQuestion);
                    ivSpeakQuestion.requestLayout();
                    ivSpeakQuestion.setFocusable(true);
                    ivSpeakQuestion.setNextFocusUpId(R.id.rl_homepage);
                    ivSpeakQuestion.setNextFocusDownId(R.id.rl_knowledge_first);
                } else {
                    ivSpeakQuestion.setImageResource(R.mipmap.song_playing_def);
//                    loseViewFocus(ivSpeakQuestion);
                }
                break;
            case R.id.rl_answer:
                if (b) {
//                    obtainViewFocus(rlAnswer);
                    ivKnowledgeAnswer.setImageResource(R.mipmap.choice_episode_focus);
                    rlAnswer.requestLayout();
                    rlAnswer.setFocusable(true);
                    rlAnswer.setNextFocusUpId(R.id.rl_knowledge_four);
                } else {
//                    loseViewFocus(rlAnswer);
                    ivKnowledgeAnswer.setImageResource(R.mipmap.choice_episode);
                }
                break;
            case R.id.rl_knowledge_first:
                if (b) {
                    obtainViewFocus(rlKnowledgeFirst);
                    rlKnowledgeFirst.requestLayout();
                    rlKnowledgeFirst.setFocusable(true);
                    rlKnowledgeFirst.setNextFocusUpId(R.id.iv_speak_question);
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
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
//                    obtainViewFocus(rlAnswer);
//                    rlAnswer.requestFocus();
//                    rlAnswer.setFocusable(true);
//                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
//                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
//                    isFirst = false;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
//                    obtainViewFocus(rlAnswer);
//                    rlAnswer.requestFocus();
//                    rlAnswer.setFocusable(true);
//                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
//                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
//                    isFirst = false;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
//                    obtainViewFocus(rlAnswer);
//                    rlAnswer.requestFocus();
//                    rlAnswer.setFocusable(true);
//                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
//                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
//                    isFirst = false;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
//                    obtainViewFocus(rlAnswer);
//                    rlAnswer.requestFocus();
//                    rlAnswer.setFocusable(true);
//                    rlAnswer.setNextFocusLeftId(R.id.rl_study);
//                    rlAnswer.setNextFocusRightId(R.id.rl_knowledge);
//                    isFirst = false;
//                }
//                break;
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

    @OnClick(R.id.iv_speak_question)
    public void onSpeakQuestion() {
        try {
            //播放 assets/a2.mp3 音乐文件
            AssetFileDescriptor fd = getAssets().openFd("knowledgeQuestion.mp3");
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
