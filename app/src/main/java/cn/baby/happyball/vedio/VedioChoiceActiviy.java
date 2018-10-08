package cn.baby.happyball.vedio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;

public class VedioChoiceActiviy extends BaseActivity implements View.OnFocusChangeListener {

    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_choice);
        ButterKnife.bind(this);
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
