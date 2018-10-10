package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.MobileCode;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DRH
 *
 */
public class VedioChoiceActiviy extends BaseActivity implements View.OnFocusChangeListener {

    private static final String CLASSID = "classes_id";
    private static final String LESSONID = "lesson_id";

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
    /**
     *
     */
    @BindView(R.id.rl_course_semester)
    RelativeLayout rlCourseSemester;
    @BindView(R.id.iv_course_semester)
    ImageView ivCourseSemester;
    @BindView(R.id.tv_course_semester_name)
    TextView tvCouseSemeterName;
    @BindView(R.id.tv_course_semester)
    TextView tvCourseSemester;
    @BindView(R.id.rl_course_semester_detail)
    RelativeLayout rlCourseSemesterDetail;
    @BindView(R.id.iv_course_semester_detail)
    ImageView ivCourseSemesterDetail;
    @BindView(R.id.tv_detail_title_semester)
    TextView tvDetailTitleSemester;
    @BindView(R.id.tv_detail_title_curriculum)
    TextView tvDetailTitleCurriculum;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;
    @BindView(R.id.tv_number_value)
    TextView tvNumber;
    @BindView(R.id.tv_last_time)
    TextView tvLastTime;
    @BindView(R.id.tv_last_time_value)
    TextView tvLastTimeValue;
    @BindView(R.id.cv_course)
    RecyclerView cvCourse;

    private int mSemesterId;
    private int mLessonId;
    private List<MobileCode> mMobileCodes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_choice);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        mSemesterId = getIntent().getIntExtra(SystemConfig.SEMESTER, 6);
        mLessonId = getIntent().getIntExtra(SystemConfig.LESSON, 6);

        Map<String, Integer> map = new HashMap<>(2);
        map.put(CLASSID, mSemesterId);
        map.put(LESSONID, mLessonId);
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.MOBILECODE)
                .post(RequestBody.create(HttpConstant.JSON, json))
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
                    mMobileCodes = JSON.parseArray(data, MobileCode.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                }
            }
        });
    }

    private void initData() {

    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioChoiceActiviy.this, MainActivity.class));
    }

    @OnClick({R.id.iv_course_semester_detail, R.id.rl_course_semester_detail, R.id.rl_course_semester, R.id.tv_course_semester})
    public void onPlayVedio() {
        startActivity(new Intent(VedioChoiceActiviy.this, VedioPlayActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
