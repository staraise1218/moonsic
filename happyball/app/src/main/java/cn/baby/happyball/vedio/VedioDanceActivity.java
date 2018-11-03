package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import cn.baby.happyball.bean.SingleDance;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VedioDanceActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final String VIDEO_EPISODE_ID = "video_episode_id";

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
     * 跳舞列表
     */
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

    private Episode mEpisode;
    private List<SingleDance> mSingleDances = new ArrayList<>(8);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_dance);
        ButterKnife.bind(this);
        bindEvents();
        getData();
    }

    public void bindEvents() {
        rlBack.setOnFocusChangeListener(this);
        rlHomePage.setOnFocusChangeListener(this);

        rlDanceFirst.setOnFocusChangeListener(this);
        ivDanceFirst.setOnFocusChangeListener(this);
        rlDanceSecond.setOnFocusChangeListener(this);
        rlDanceThird.setOnFocusChangeListener(this);
        rlDanceFour.setOnFocusChangeListener(this);
        rlDanceFive.setOnFocusChangeListener(this);
        rlDanceSix.setOnFocusChangeListener(this);
        rlDanceSeven.setOnFocusChangeListener(this);
        rlDanceEight.setOnFocusChangeListener(this);
    }

    public void getData() {
        mEpisode = (Episode) getIntent().getSerializableExtra(SystemConfig.EPISODE);

        Map<String, Integer> map = new HashMap<>(1);
        map.put(VIDEO_EPISODE_ID, Integer.valueOf(mEpisode.getEpisode()));
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.MOBILDECODE)
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
                    mSingleDances = JSON.parseArray(data, SingleDance.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                }
            }
        });
    }

    public void initData() {

    }

    @OnClick({R.id.iv_back, R.id.rl_back})
    public void onBack() {
        startActivity(new Intent(VedioDanceActivity.this, VedioStudyActivity.class));
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioDanceActivity.this, MainActivity.class));
    }

    @OnClick({R.id.rl_dance_first, R.id.iv_dance_first})
    public void onDanceFirst() {

    }

    @OnClick({R.id.rl_dance_second, R.id.iv_dance_second})
    public void onDanceSecond() {

    }

    @OnClick({R.id.rl_dance_third, R.id.iv_dance_third})
    public void onDanceThird() {

    }

    @OnClick({R.id.rl_dance_four, R.id.iv_dance_four})
    public void onDanceFour() {

    }

    @OnClick({R.id.rl_dance_five, R.id.iv_dance_five})
    public void onDanceFive() {

    }

    @OnClick({R.id.rl_dance_six, R.id.iv_dance_six})
    public void onDanceSix() {

    }

    @OnClick({R.id.rl_dance_seven, R.id.iv_dance_seven})
    public void onDanceSeven() {

    }

    @OnClick({R.id.rl_dance_eight, R.id.iv_dance_eight})
    public void onDanceEight() {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_dance_first:
                if (b) {
                    obtainViewFocus(rlDanceFirst);
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance_second);
                    rlDanceFirst.setNextFocusDownId(R.id.rl_dance_five);
                } else {
                    loseViewFocus(rlDanceFirst);
                }
                break;
            case R.id.rl_dance_second:
                if (b) {
                    obtainViewFocus(rlDanceSecond);
                    rlDanceSecond.setNextFocusLeftId(R.id.rl_dance_first);
                    rlDanceSecond.setNextFocusRightId(R.id.rl_dance_third);
                    rlDanceSecond.setNextFocusDownId(R.id.rl_dance_six);
                } else {
                    loseViewFocus(rlDanceSecond);
                }
                break;
            case R.id.rl_dance_third:
                if (b) {
                    obtainViewFocus(rlDanceThird);
                    rlDanceThird.setNextFocusLeftId(R.id.rl_dance_second);
                    rlDanceThird.setNextFocusRightId(R.id.rl_dance_four);
                    rlDanceThird.setNextFocusDownId(R.id.rl_dance_seven);
                } else {
                    loseViewFocus(rlDanceThird);
                }
                break;
            case R.id.rl_dance_four:
                if (b) {
                    obtainViewFocus(rlDanceFour);
                    rlDanceFour.setNextFocusLeftId(R.id.rl_dance_third);
                    rlDanceFour.setNextFocusRightId(R.id.rl_dance_five);
                    rlDanceFour.setNextFocusDownId(R.id.rl_dance_eight);
                } else {
                    loseViewFocus(rlDanceFour);
                }
                break;
            case R.id.rl_dance_five:
                if (b) {
                    obtainViewFocus(rlDanceFive);
                    rlDanceFive.setNextFocusLeftId(R.id.rl_dance_four);
                    rlDanceFive.setNextFocusRightId(R.id.rl_dance_six);
                    rlDanceFive.setNextFocusUpId(R.id.rl_dance_first);
                } else {
                    loseViewFocus(rlDanceFive);
                }
                break;
            case R.id.rl_dance_six:
                if (b) {
                    obtainViewFocus(rlDanceSix);
                    rlDanceSix.setNextFocusLeftId(R.id.rl_dance_five);
                    rlDanceSix.setNextFocusRightId(R.id.rl_dance_seven);
                    rlDanceSix.setNextFocusUpId(R.id.rl_dance_second);
                } else {
                    loseViewFocus(rlDanceSix);
                }
                break;
            case R.id.rl_dance_seven:
                if (b) {
                    obtainViewFocus(rlDanceSeven);
                    rlDanceSeven.setNextFocusLeftId(R.id.rl_dance_six);
                    rlDanceSeven.setNextFocusRightId(R.id.rl_dance_eight);
                    rlDanceSeven.setNextFocusUpId(R.id.rl_dance_third);
                } else {
                    loseViewFocus(rlDanceSeven);
                }
                break;
            case R.id.rl_dance_eight:
                if (b) {
                    obtainViewFocus(rlDanceEight);
                    rlDanceEight.setNextFocusLeftId(R.id.rl_dance_seven);
                    rlDanceEight.setNextFocusUpId(R.id.rl_dance_four);
                } else {
                    loseViewFocus(rlDanceEight);
                }
                break;
            case R.id.rl_back:
                if (b) {
                    obtainViewFocus(rlBack);
                    rlBack.setNextFocusDownId(R.id.rl_dance_first);
                    rlBack.setNextFocusUpId(R.id.rl_homepage);
                } else {
                    loseViewFocus(rlBack);
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    obtainViewFocus(rlHomePage);
                    rlHomePage.setNextFocusDownId(R.id.rl_dance_four);
                    rlHomePage.setNextFocusLeftId(R.id.rl_back);
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

//    boolean isFirst = true;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_CENTER:
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if (isFirst) {
//                    obtainViewFocus(rlDanceFirst);
//                    rlDanceFirst.requestFocus();
//                    rlDanceFirst.setFocusable(true);
//                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
//                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance);
//                    isFirst = false;
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
