package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.squareup.picasso.Picasso;

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
public class VedioCurriculumActivity extends BaseActivity {

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomaPage;
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

    private int mSemesterId;
    List<Lesson> mLessons = new ArrayList<>(6);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_curriculum);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        mSemesterId = getIntent().getIntExtra(SystemConfig.SEMESTER, 6);

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.LESSON)
                .post(RequestBody.create(HttpConstant.JSON, ""))
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errString = e.toString();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                try {
                    String data = (new JSONObject(responseStr)).optString("data");
                    mLessons = JSON.parseArray(data, Lesson.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {

                }
            }
        });
    }

    private void initData() {
        for (Lesson lesson : mLessons) {
            String imageUrl = HttpConstant.IMGURL + lesson.getImage();
            switch (lesson.getId()) {
                case 1:
                    tvChina.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivChina);
                    break;

                case 2:
                    tvWorld.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivWorld);
                    break;

                case 3:
                    tvPop.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivPop);
                    break;

                case 4:
                    tvNation.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivNation);
                    break;

                case 5:
                    tvHygiene.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivHygiene);
                    break;

                case 6 :
                    tvSafe.setText(lesson.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivSafe);
                    break;

                default:break;
            }
        }
    }

    @OnClick({R.id.rl_homepage, R.id.iv_homepage})
    public void onHomePage() {
        startActivity(new Intent(VedioCurriculumActivity.this, MainActivity.class));
    }

    @OnClick({R.id.rl_back, R.id.iv_back})
    public void onBack() {
        startActivity(new Intent(VedioCurriculumActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_safe, R.id.rl_safe})
    public void onSafe() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 6));
    }

    @OnClick({R.id.iv_hygiene, R.id.rl_hygiene})
    public void onHygiene() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 5));
    }

    @OnClick({R.id.iv_nation, R.id.rl_nation})
    public void onNation() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 4));
    }

    @OnClick({R.id.iv_pop, R.id.rl_pop})
    public void onPop() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 3));
    }

    @OnClick({R.id.iv_world, R.id.rl_world})
    public void onWorld() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 2));
    }

    @OnClick({R.id.iv_china, R.id.rl_china})
    public void onChina() {
        startActivity(new Intent(VedioCurriculumActivity.this, VedioChoiceActiviy.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 1));
    }
}
