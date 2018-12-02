package cn.baby.happyball.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.adapter.EpisodeAdapter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DRH
 */
public class VedioChoiceActiviy extends BaseActivity implements View.OnFocusChangeListener {

    private static final String CLASSES_ID = "classes_id";

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;

    /**
     * 班级课程
     */
    @BindView(R.id.rl_course_semester)
    RelativeLayout rlCourseSemester;
    @BindView(R.id.iv_course_semester)
    ImageView ivCourseSemester;
    @BindView(R.id.tv_semester_name)
    TextView tvSemeterName;
    @BindView(R.id.tv_lesson_name)
    TextView tvLessonName;

    /**
     * 班级课程描述
     */
    @BindView(R.id.rl_course_semester_detail)
    RelativeLayout rlCourseSemesterDetail;
    @BindView(R.id.iv_course_semester_detail)
    ImageView ivCourseSemesterDetail;
    @BindView(R.id.tv_detail_title_semester)
    TextView tvDetailTitleSemester;
    @BindView(R.id.tv_detail_title_lesson)
    TextView tvDetailTitleLesson;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;

    /**
     * 历史纪录
     */
    @BindView(R.id.tv_number_value)
    TextView tvNumber;
    @BindView(R.id.tv_last_time)
    TextView tvLastTime;
    @BindView(R.id.tv_last_time_value)
    TextView tvLastTimeValue;

    /**
     * 集数选择
     */

    private StaggeredGridLayoutManager mLayoutManager;
    private Semester mSemester;
    private Lesson mLesson;
    private List<Episode> mEpisodes = new ArrayList<>();
    private EpisodeAdapter mEpisodeAdapter;
    private String mLastNum, mLastTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_choice_activity);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        mSemester = (Semester) getIntent().getSerializableExtra(SystemConfig.SEMESTER);
        mLesson = (Lesson) getIntent().getSerializableExtra(SystemConfig.LESSON);

        Map<String, Integer> map = new HashMap<>(2);
        map.put(CLASSES_ID, mSemester.getId());
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.VIDEO_EPISODE)
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
                    mEpisodes = JSON.parseArray(data, Episode.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                }
            }
        });
    }

    private void initData() {
        tvSemeterName.setText(mSemester.getName());
        tvDetailTitleSemester.setText(mSemester.getName());
        tvLessonName.setText(mLesson.getName());
        tvDetailTitleLesson.setText(mLesson.getName());
        tvDetailContent.setText(mLesson.getDescription());
        tvNumber.setText(String.format(getString(R.string.number_value), mEpisodes.size()));
        mLastNum = getValue(SystemConfig.EPISODE_NUM);
        mLastTime = getValue(SystemConfig.EPISODE_TIME);
        if ("".equalsIgnoreCase(mLastNum)) {
            tvLastTime.setText(R.string.last_no_playing);
            tvLastTimeValue.setVisibility(View.INVISIBLE);
        } else {
            tvLastTime.setText(String.format(getString(R.string.last_time), Integer.valueOf(mLastNum)));
            tvLastTimeValue.setVisibility(View.VISIBLE);
            tvLastTimeValue.setText(mLastTime);
        }
        String imageUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mLesson.getImage())).toString();
        loadImage(imageUrl, ivCourseSemester, R.mipmap.choice_course_semester);
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioChoiceActiviy.this, MainActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
