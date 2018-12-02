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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DRH
 */
public class VedioLessonActivity extends BaseActivity implements View.OnFocusChangeListener {

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;

    /**
     * 幼儿安全教育
     */
    @BindView(R.id.rl_safe)
    RelativeLayout rlSafe;
    @BindView(R.id.iv_safe)
    ImageView ivSafe;
    @BindView(R.id.tv_safe_name)
    TextView tvSafe;

    /**
     * 幼儿卫生常识
     */
    @BindView(R.id.rl_hygiene)
    RelativeLayout rlHygiene;
    @BindView(R.id.iv_hygiene)
    ImageView ivHygiene;
    @BindView(R.id.tv_hygiene_name)
    TextView tvHygiene;

    /**
     * 民族列车
     */
    @BindView(R.id.rl_nation)
    RelativeLayout rlNation;
    @BindView(R.id.iv_nation)
    ImageView ivNation;
    @BindView(R.id.tv_nation_name)
    TextView tvNation;

    /**
     * 流行歌舞
     */
    @BindView(R.id.rl_pop)
    RelativeLayout rlPop;
    @BindView(R.id.iv_pop)
    ImageView ivPop;
    @BindView(R.id.tv_pop_name)
    TextView tvPop;

    /**
     * 世界魅力
     */
    @BindView(R.id.rl_world)
    RelativeLayout rlWorld;
    @BindView(R.id.iv_world)
    ImageView ivWorld;
    @BindView(R.id.tv_world_name)
    TextView tvWorld;

    /**
     * 中国魅力
     */
    @BindView(R.id.rl_china)
    RelativeLayout rlChina;
    @BindView(R.id.iv_china)
    ImageView ivChina;
    @BindView(R.id.tv_china_name)
    TextView tvChina;

    /**
     *  加载
     */
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    private Semester mSemester;
    List<Lesson> mLessons = new ArrayList<>(6);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_lesson_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
    }

    private void bindEvents() {
        rlSafe.setOnFocusChangeListener(this);
        rlHygiene.setOnFocusChangeListener(this);
        rlNation.setOnFocusChangeListener(this);
        rlPop.setOnFocusChangeListener(this);
        rlWorld.setOnFocusChangeListener(this);
        rlChina.setOnFocusChangeListener(this);
    }

    public void getData() {
        showLoading(true);
        mSemester = (Semester) getIntent().getSerializableExtra(SystemConfig.SEMESTER);
        String url = (new StringBuilder().append(HttpConstant.URL).append(HttpConstant.VIDEO_LESSON)).toString();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(HttpConstant.JSON, ""))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> showLoading(false));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseStr = response.body().string();
                    String data = (new JSONObject(responseStr)).optString("data");
                    mLessons = JSON.parseArray(data, Lesson.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> showLoading(false));
                }
            }
        });
    }

    private void initData() {
        for (Lesson lesson : mLessons) {
            String imageUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(lesson.getImage())).toString();
            switch (lesson.getId()) {
                case 1:
                    tvChina.setText(lesson.getName());
                    loadImage(imageUrl, ivChina, R.mipmap.main_big_next);
                    break;

                case 2:
                    tvWorld.setText(lesson.getName());
                    loadImage(imageUrl, ivWorld, R.mipmap.main_middle_next);
                    break;

                case 3:
                    tvPop.setText(lesson.getName());
                    loadImage(imageUrl, ivPop, R.mipmap.main_reception_next);
                    break;

                case 4:
                    tvNation.setText(lesson.getName());
                    loadImage(imageUrl, ivNation, R.mipmap.main_big_last);
                    break;

                case 5:
                    tvHygiene.setText(lesson.getName());
                    loadImage(imageUrl, ivHygiene, R.mipmap.main_middle_last);
                    break;

                case 6 :
                    tvSafe.setText(lesson.getName());
                    loadImage(imageUrl, ivSafe, R.mipmap.main_reception_last);
                    break;

                default:break;
            }
        }
        showLoading(false);

        obtainViewFocus(rlSafe);
        rlSafe.requestFocus();
        rlSafe.setFocusable(true);
        rlSafe.setNextFocusDownId(R.id.rl_pop);
        rlSafe.setNextFocusRightId(R.id.rl_hygiene);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_safe:
                if (b) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                } else {
                    loseViewFocus(rlSafe);
                }
                break;
            case R.id.rl_hygiene:
                if (b) {
                    obtainViewFocus(rlHygiene);
                    rlHygiene.setNextFocusLeftId(R.id.rl_safe);
                    rlHygiene.setNextFocusRightId(R.id.rl_nation);
                    rlHygiene.setNextFocusDownId(R.id.rl_world);
                } else {
                    loseViewFocus(rlHygiene);
                }
                break;
            case R.id.rl_nation:
                if (b) {
                    obtainViewFocus(rlNation);
                    rlNation.setNextFocusLeftId(R.id.rl_hygiene);
                    rlNation.setNextFocusRightId(R.id.rl_pop);
                    rlNation.setNextFocusDownId(R.id.rl_china);
                } else {
                    loseViewFocus(rlNation);
                }
                break;
            case R.id.rl_pop:
                if (b) {
                    obtainViewFocus(rlPop);
                    rlPop.setNextFocusLeftId(R.id.rl_nation);
                    rlPop.setNextFocusRightId(R.id.rl_world);
                    rlPop.setNextFocusUpId(R.id.rl_safe);
                } else {
                    loseViewFocus(rlPop);
                }
                break;
            case R.id.rl_world:
                if (b) {
                    obtainViewFocus(rlWorld);
                    rlWorld.setNextFocusLeftId(R.id.rl_pop);
                    rlWorld.setNextFocusRightId(R.id.rl_china);
                    rlWorld.setNextFocusUpId(R.id.rl_hygiene);
                } else {
                    loseViewFocus(rlWorld);
                }
                break;
            case R.id.rl_china:
                if (b) {
                    obtainViewFocus(rlChina);
                    rlChina.setNextFocusLeftId(R.id.rl_world);
                    rlChina.setNextFocusUpId(R.id.rl_nation);
                } else {
                    loseViewFocus(rlChina);
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
                if (isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
                default:
                    break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.rl_homepage, R.id.iv_homepage})
    public void onHomePage() {
        startActivity(new Intent(VedioLessonActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_safe, R.id.rl_safe})
    public void onSafe() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(0)));
    }

    @OnClick({R.id.iv_hygiene, R.id.rl_hygiene})
    public void onHygiene() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(1)));
    }

    @OnClick({R.id.iv_nation, R.id.rl_nation})
    public void onNation() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(2)));
    }

    @OnClick({R.id.iv_pop, R.id.rl_pop})
    public void onPop() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(3)));
    }

    @OnClick({R.id.iv_world, R.id.rl_world})
    public void onWorld() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(4)));
    }

    @OnClick({R.id.iv_china, R.id.rl_china})
    public void onChina() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemester)
                .putExtra(SystemConfig.LESSON, mLessons.get(5)));
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
