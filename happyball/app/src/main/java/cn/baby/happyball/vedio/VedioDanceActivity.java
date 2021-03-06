package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

    /**
     *  加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Episode mEpisode;
    private List<SingleDance> mSingleDances = new ArrayList<>(8);
    private ArrayList<String> mSingleDanceUrls = new ArrayList<>(8);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_dance_activity);
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
                .url(HttpConstant.URL + HttpConstant.VIDEO_SINGLEDANCE)
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
                    mSingleDances = JSON.parseArray(data, SingleDance.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> initData());
                }
            }
        });
    }

    public void initData() {
        switch (mSingleDances.size()) {
            case 1:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setVisibility(View.GONE);
                ivDanceSecond.setVisibility(View.GONE);
                tvDanceThird.setVisibility(View.GONE);
                ivDanceThird.setVisibility(View.GONE);
                tvDanceFour.setVisibility(View.GONE);
                ivDanceFour.setVisibility(View.GONE);
                tvDanceFive.setVisibility(View.GONE);
                ivDanceFive.setVisibility(View.GONE);
                tvDanceSix.setVisibility(View.GONE);
                ivDanceSix.setVisibility(View.GONE);
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 2:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setVisibility(View.GONE);
                ivDanceThird.setVisibility(View.GONE);
                tvDanceFour.setVisibility(View.GONE);
                ivDanceFour.setVisibility(View.GONE);
                tvDanceFive.setVisibility(View.GONE);
                ivDanceFive.setVisibility(View.GONE);
                tvDanceSix.setVisibility(View.GONE);
                ivDanceSix.setVisibility(View.GONE);
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 3:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setVisibility(View.GONE);
                ivDanceFour.setVisibility(View.GONE);
                tvDanceFive.setVisibility(View.GONE);
                ivDanceFive.setVisibility(View.GONE);
                tvDanceSix.setVisibility(View.GONE);
                ivDanceSix.setVisibility(View.GONE);
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 4:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setText(mSingleDances.get(3).getTitle());
                tvDanceFive.setVisibility(View.GONE);
                ivDanceFive.setVisibility(View.GONE);
                tvDanceSix.setVisibility(View.GONE);
                ivDanceSix.setVisibility(View.GONE);
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 5:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setText(mSingleDances.get(3).getTitle());
                tvDanceFive.setText(mSingleDances.get(4).getTitle());
                tvDanceSix.setVisibility(View.GONE);
                ivDanceSix.setVisibility(View.GONE);
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 6:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setText(mSingleDances.get(3).getTitle());
                tvDanceFive.setText(mSingleDances.get(4).getTitle());
                tvDanceSix.setText(mSingleDances.get(5).getTitle());
                tvDanceSeven.setVisibility(View.GONE);
                ivDanceSeven.setVisibility(View.GONE);
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 7:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setText(mSingleDances.get(3).getTitle());
                tvDanceFive.setText(mSingleDances.get(4).getTitle());
                tvDanceSix.setText(mSingleDances.get(5).getTitle());
                tvDanceSeven.setText(mSingleDances.get(6).getTitle());
                tvDanceEight.setVisibility(View.GONE);
                ivDanceEight.setVisibility(View.GONE);
                break;
            case 8:
                tvDanceFirst.setText(mSingleDances.get(0).getTitle());
                tvDanceSecond.setText(mSingleDances.get(1).getTitle());
                tvDanceThird.setText(mSingleDances.get(2).getTitle());
                tvDanceFour.setText(mSingleDances.get(3).getTitle());
                tvDanceFive.setText(mSingleDances.get(4).getTitle());
                tvDanceSix.setText(mSingleDances.get(5).getTitle());
                tvDanceSeven.setText(mSingleDances.get(6).getTitle());
                tvDanceEight.setText(mSingleDances.get(7).getTitle());
                break;
                default:
                    tvDanceFirst.setVisibility(View.GONE);
                    ivDanceFirst.setVisibility(View.GONE);
                    tvDanceSecond.setVisibility(View.GONE);
                    ivDanceSecond.setVisibility(View.GONE);
                    tvDanceThird.setVisibility(View.GONE);
                    ivDanceThird.setVisibility(View.GONE);
                    tvDanceFour.setVisibility(View.GONE);
                    ivDanceFour.setVisibility(View.GONE);
                    tvDanceFive.setVisibility(View.GONE);
                    ivDanceFive.setVisibility(View.GONE);
                    tvDanceSix.setVisibility(View.GONE);
                    ivDanceSix.setVisibility(View.GONE);
                    tvDanceSeven.setVisibility(View.GONE);
                    ivDanceSeven.setVisibility(View.GONE);
                    tvDanceEight.setVisibility(View.GONE);
                    ivDanceEight.setVisibility(View.GONE);
                    break;
        }

        for (SingleDance singleDance : mSingleDances) {
            String videoUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(singleDance.getVideo())).toString();
            mSingleDanceUrls.add(videoUrl);
        }
        showLoading(false);
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
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 0));
    }

    @OnClick({R.id.rl_dance_second, R.id.iv_dance_second})
    public void onDanceSecond() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 1));
    }

    @OnClick({R.id.rl_dance_third, R.id.iv_dance_third})
    public void onDanceThird() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 2));
    }

    @OnClick({R.id.rl_dance_four, R.id.iv_dance_four})
    public void onDanceFour() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 3));
    }

    @OnClick({R.id.rl_dance_five, R.id.iv_dance_five})
    public void onDanceFive() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 4));
    }

    @OnClick({R.id.rl_dance_six, R.id.iv_dance_six})
    public void onDanceSix() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 5));
    }

    @OnClick({R.id.rl_dance_seven, R.id.iv_dance_seven})
    public void onDanceSeven() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 6));
    }

    @OnClick({R.id.rl_dance_eight, R.id.iv_dance_eight})
    public void onDanceEight() {
        startActivity(new Intent(VedioDanceActivity.this, SingleDancePlayActivity.class)
                .putStringArrayListExtra(SystemConfig.SINGLE_DANCE, mSingleDanceUrls)
                .putExtra(SystemConfig.SINGLE_DANCE_PLAYING_INDEXX, 7));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_dance_first:
                if (b) {
                    ivDanceFirst.setImageResource(R.mipmap.main_reception_focus);
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance_second);
                    rlDanceFirst.setNextFocusDownId(R.id.rl_dance_five);
                } else {
                    ivDanceFirst.setImageResource(R.mipmap.main_reception);
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_second:
                if (b) {
                    ivDanceSecond.setImageResource(R.mipmap.main_middle_focus);
                    tvDanceSecond.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceSecond.setNextFocusLeftId(R.id.rl_dance_first);
                    rlDanceSecond.setNextFocusRightId(R.id.rl_dance_third);
                    rlDanceSecond.setNextFocusDownId(R.id.rl_dance_six);
                } else {
                    ivDanceSecond.setImageResource(R.mipmap.main_middle);
                    tvDanceSecond.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_third:
                if (b) {
                    ivDanceThird.setImageResource(R.mipmap.main_big_focus);
                    tvDanceThird.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceThird.setNextFocusLeftId(R.id.rl_dance_second);
                    rlDanceThird.setNextFocusRightId(R.id.rl_dance_four);
                    rlDanceThird.setNextFocusDownId(R.id.rl_dance_seven);
                } else {
                    ivDanceThird.setImageResource(R.mipmap.main_big);
                    tvDanceThird.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_four:
                if (b) {
                    ivDanceFour.setImageResource(R.mipmap.main_reception_focus);
                    tvDanceFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFour.setNextFocusLeftId(R.id.rl_dance_third);
                    rlDanceFour.setNextFocusRightId(R.id.rl_dance_five);
                    rlDanceFour.setNextFocusDownId(R.id.rl_dance_eight);
                } else {
                    ivDanceFour.setImageResource(R.mipmap.main_reception);
                    tvDanceFour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_five:
                if (b) {
                    ivDanceFive.setImageResource(R.mipmap.main_reception_focus);
                    tvDanceFive.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFive.setNextFocusLeftId(R.id.rl_dance_four);
                    rlDanceFive.setNextFocusRightId(R.id.rl_dance_six);
                    rlDanceFive.setNextFocusUpId(R.id.rl_dance_first);
                } else {
                    ivDanceFive.setImageResource(R.mipmap.main_reception);
                    tvDanceFive.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_six:
                if (b) {
                    ivDanceSix.setImageResource(R.mipmap.main_middle_focus);
                    tvDanceSix.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceSix.setNextFocusLeftId(R.id.rl_dance_five);
                    rlDanceSix.setNextFocusRightId(R.id.rl_dance_seven);
                    rlDanceSix.setNextFocusUpId(R.id.rl_dance_second);
                } else {
                    ivDanceSix.setImageResource(R.mipmap.main_middle);
                    tvDanceSix.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_seven:
                if (b) {
                    ivDanceSeven.setImageResource(R.mipmap.main_big_focus);
                    tvDanceSeven.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceSeven.setNextFocusLeftId(R.id.rl_dance_six);
                    rlDanceSeven.setNextFocusRightId(R.id.rl_dance_eight);
                    rlDanceSeven.setNextFocusUpId(R.id.rl_dance_third);
                } else {
                    ivDanceSeven.setImageResource(R.mipmap.main_big);
                    tvDanceSeven.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_dance_eight:
                if (b) {
                    ivDanceEight.setImageResource(R.mipmap.main_middle_focus);
                    tvDanceEight.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceEight.setNextFocusLeftId(R.id.rl_dance_seven);
                    rlDanceEight.setNextFocusUpId(R.id.rl_dance_four);
                } else {
                    ivDanceEight.setImageResource(R.mipmap.main_middle);
                    tvDanceEight.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
                break;
            case R.id.rl_back:
                if (b) {
                    rlBack.setNextFocusDownId(R.id.rl_dance_first);
                    rlBack.setNextFocusUpId(R.id.rl_homepage);
                } else {
                }
                break;
            case R.id.rl_homepage:
                if (b) {
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                    rlHomePage.setNextFocusDownId(R.id.rl_dance_first);
                    rlHomePage.setNextFocusLeftId(R.id.rl_dance_first);
                    rlHomePage.setNextFocusRightId(R.id.rl_dance_first);
                } else {
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
                }
                break;
            default:
                break;
        }
    }

    boolean isFirst = true;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFirst.requestFocus();
                    rlDanceFirst.setFocusable(true);
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFirst.requestFocus();
                    rlDanceFirst.setFocusable(true);
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFirst.requestFocus();
                    rlDanceFirst.setFocusable(true);
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    tvDanceFirst.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.playing_text));
                    rlDanceFirst.requestFocus();
                    rlDanceFirst.setFocusable(true);
                    rlDanceFirst.setNextFocusUpId(R.id.rl_back);
                    rlDanceFirst.setNextFocusRightId(R.id.rl_dance);
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

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
