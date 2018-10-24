package cn.baby.happyball.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
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
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.VedioPlayActivity;
import cn.baby.happyball.vedio.adapter.EpisodeAdapter;
import cn.baby.happyball.view.recyclerView.TvRecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DRH
 */
public class AudioChoiceActiviy extends BaseActivity implements View.OnFocusChangeListener {

    private static final String CLASSES_ID = "classes_id";
    private static final String LESSON_ID = "lesson_id";
    private static final int LINE_NUM = 2;

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
    @BindView(R.id.tv_number_value)
    TextView tvNumber;
    @BindView(R.id.tv_last_time)
    TextView tvLastTime;
    @BindView(R.id.tv_last_time_value)
    TextView tvLastTimeValue;
    @BindView(R.id.cv_course)
    TvRecyclerView cvCourse;

    private StaggeredGridLayoutManager mLayoutManager;
    private Semester mSemester;
    private Lesson mLesson;
    private List<Episode> mEpisodes = new ArrayList<>();
    private EpisodeAdapter mEpisodeAdapter;
    private String mLastNum, mLastTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_choice);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        mSemester = (Semester) getIntent().getSerializableExtra(SystemConfig.SEMESTER);
        mLesson = (Lesson) getIntent().getSerializableExtra(SystemConfig.LESSON);

        Map<String, Integer> map = new HashMap<>(2);
        map.put(CLASSES_ID, mSemester.getId());
        map.put(LESSON_ID, mLesson.getId());
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.AUDIO_EPISODE)
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
        Picasso.with(getApplicationContext()).load(imageUrl).into(ivCourseSemester);

        cvCourse.setItemAnimator(new DefaultItemAnimator());
        mEpisodeAdapter = new EpisodeAdapter(AudioChoiceActiviy.this, mEpisodes);
        mLayoutManager = new StaggeredGridLayoutManager(LINE_NUM, StaggeredGridLayoutManager.HORIZONTAL);
        mLayoutManager.setAutoMeasureEnabled(true);
        cvCourse.setLayoutManager(mLayoutManager);
        cvCourse.setAdapter(mEpisodeAdapter);
        mEpisodeAdapter.setOnItemClickListener(mItemClickListener);
        cvCourse.setOnScrollListener(mScrollListener);
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(AudioChoiceActiviy.this, MainActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    private EpisodeAdapter.OnItemClickListener mItemClickListener = new EpisodeAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            StringBuilder builder = new StringBuilder();
            builder.append(SystemConfig.EPISODE_NUM).append(mSemester.getId()).append(mLesson.getId());
            setValue(builder.toString(), mEpisodes.get(position).getEpisode());
            startActivity(new Intent(AudioChoiceActiviy.this, AudioPlayActivity.class)
                    .putExtra(SystemConfig.EPISODE, mEpisodes.get(position))
                    .putExtra(SystemConfig.SEMESTER, mSemester)
                    .putExtra(SystemConfig.LESSON, mLesson));
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };
}
