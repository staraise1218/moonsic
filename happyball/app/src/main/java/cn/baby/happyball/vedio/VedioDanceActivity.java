package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        if (b) {
            obtainViewFocus(view);
        } else {
            loseViewFocus(view);
        }
    }
}
