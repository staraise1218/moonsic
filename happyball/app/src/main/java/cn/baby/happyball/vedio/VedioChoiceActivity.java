package cn.baby.happyball.vedio;

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
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
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
public class VedioChoiceActivity extends BaseActivity implements View.OnFocusChangeListener {

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
    @BindView(R.id.cv_course)
    TvRecyclerView cvCourse;

    private StaggeredGridLayoutManager mLayoutManager;
    private int mSemesterId;
    private int mLessonId;
    private List<Episode> mEpisodes = new ArrayList<>();
    private EpisodeAdapter mEpisodeAdapter;
    private String mLastNum, mLastTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_choice_activity);
        ButterKnife.bind(this);
        rlHomePage.setOnFocusChangeListener(this);
        getData();
    }

    private void getData() {
        mSemesterId = getIntent().getIntExtra(SystemConfig.SEMESTER, 1);
        mLessonId = getIntent().getIntExtra(SystemConfig.LESSON, 1);

        loadBitmap();

        Map<String, Integer> map = new HashMap<>(2);
        map.put(CLASSES_ID, 6);
        map.put(LESSON_ID, 6);
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
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
        setTittle();

        if (!mEpisodes.isEmpty()) {
            tvNumber.setVisibility(View.VISIBLE);
            tvNumber.setText(String.format(getString(R.string.number_value), mEpisodes.size()));
            mLastNum = getValue(SystemConfig.EPISODE_NUM);
            mLastTime = getValue(SystemConfig.EPISODE_TIME);
            if ("".equalsIgnoreCase(mLastNum)) {
                tvLastTime.setVisibility(View.VISIBLE);
                tvLastTime.setText(R.string.last_no_playing);
                tvLastTimeValue.setVisibility(View.INVISIBLE);
            } else {
                tvLastTime.setVisibility(View.VISIBLE);
                tvLastTime.setText(String.format(getString(R.string.last_time), Integer.valueOf(mLastNum)));
                tvLastTimeValue.setVisibility(View.VISIBLE);
                tvLastTimeValue.setText(mLastTime);
            }

            cvCourse.setItemAnimator(new DefaultItemAnimator());
            mEpisodeAdapter = new EpisodeAdapter(VedioChoiceActivity.this, mEpisodes);
            mLayoutManager = new StaggeredGridLayoutManager(LINE_NUM, StaggeredGridLayoutManager.HORIZONTAL);
            mLayoutManager.setAutoMeasureEnabled(true);
            cvCourse.setLayoutManager(mLayoutManager);
            cvCourse.setAdapter(mEpisodeAdapter);
            mEpisodeAdapter.setOnItemClickListener(mItemClickListener);
            cvCourse.setOnScrollListener(mScrollListener);
        } else {
            tvNumber.setVisibility(View.INVISIBLE);
            tvLastTime.setVisibility(View.INVISIBLE);
            tvLastTimeValue.setVisibility(View.INVISIBLE);
        }
    }

    private void setTittle() {
        switch (mSemesterId) {
            case 1:
                tvSemeterName.setText(R.string.reception_last_semester);
                tvDetailTitleSemester.setText(R.string.reception_last_semester);
                break;
            case 2:
                tvSemeterName.setText(R.string.middle_last_semester);
                tvDetailTitleSemester.setText(R.string.middle_last_semester);
                break;
            case 3:
                tvSemeterName.setText(R.string.big_last_semester);
                tvDetailTitleSemester.setText(R.string.big_last_semester);
                break;
            case 4:
                tvSemeterName.setText(R.string.reception_next_semester);
                tvDetailTitleSemester.setText(R.string.reception_next_semester);
                break;
            case 5:
                tvSemeterName.setText(R.string.middle_next_semester);
                tvDetailTitleSemester.setText(R.string.middle_next_semester);
                break;
            case 6:
                tvSemeterName.setText(R.string.big_next_semester);
                tvDetailTitleSemester.setText(R.string.big_next_semester);
                break;
            default:
                break;
        }
        switch (mLessonId) {
            case 1:
                tvLessonName.setText(R.string.lesson_safe);
                tvDetailTitleLesson.setText(R.string.lesson_safe);
                break;
            case 2:
                tvLessonName.setText(R.string.lesson_hygiene);
                tvDetailTitleLesson.setText(R.string.lesson_hygiene);
                break;
            case 3:
                tvLessonName.setText(R.string.lesson_nation);
                tvDetailTitleLesson.setText(R.string.lesson_nation);
                break;
            case 4:
                tvLessonName.setText(R.string.lesson_pop);
                tvDetailTitleLesson.setText(R.string.lesson_pop);
                break;
            case 5:
                tvLessonName.setText(R.string.lesson_world);
                tvDetailTitleLesson.setText(R.string.lesson_world);
                break;
            case 6:
                tvLessonName.setText(R.string.lesson_china);
                tvDetailTitleLesson.setText(R.string.lesson_china);
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(VedioChoiceActivity.this, MainActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.rl_homepage) {
            if (b) {
                ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
            } else {
                ivHomePage.setImageResource(R.mipmap.choice_episode);
            }
        }
    }

    private EpisodeAdapter.OnItemClickListener mItemClickListener = new EpisodeAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            StringBuilder builder = new StringBuilder();
            builder.append(SystemConfig.EPISODE_NUM).append(mSemesterId).append(mLessonId);
            setValue(builder.toString(), mEpisodes.get(position).getEpisode());
            startActivity(new Intent(VedioChoiceActivity.this, VedioPlayActivity.class)
                    .putExtra(SystemConfig.EPISODE, mEpisodes.get(position))
                    .putExtra(SystemConfig.SEMESTER, mSemesterId)
                    .putExtra(SystemConfig.LESSON, mLessonId));
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

    private void loadBitmap() {
        switch (mSemesterId) {
            case 1:
            case 4:
                ivCourseSemester.setImageResource(R.mipmap.main_reception);
                break;
            case 2:
            case 5:
                ivCourseSemester.setImageResource(R.mipmap.main_middle);
                break;
            case 3:
            case 6:
                ivCourseSemester.setImageResource(R.mipmap.main_big);
                break;
            default:
                ivCourseSemester.setImageResource(R.mipmap.main_reception);
                break;
        }
    }
}
