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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.BaseActivity;
import cn.baby.happyball.MainActivity;
import cn.baby.happyball.R;
import cn.baby.happyball.constant.SystemConfig;

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

    private int mSemesterId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_lesson_activity);
        ButterKnife.bind(this);
        bindEvents();
        getData();
        initData();
    }

    private void bindEvents() {
        rlHomePage.setOnFocusChangeListener(this);
        rlSafe.setOnFocusChangeListener(this);
        rlHygiene.setOnFocusChangeListener(this);
        rlNation.setOnFocusChangeListener(this);
        rlPop.setOnFocusChangeListener(this);
        rlWorld.setOnFocusChangeListener(this);
        rlChina.setOnFocusChangeListener(this);
    }

    public void getData() {
        new LoadBitmapAsyncTask(mLoadBitmepListener).execute();
        mSemesterId = getIntent().getIntExtra(SystemConfig.SEMESTER, 1);
    }

    private void initData() {
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
            case R.id.rl_homepage:
                if (b) {
                    ivHomePage.setImageResource(R.mipmap.choice_episode_focus);
                } else {
                    ivHomePage.setImageResource(R.mipmap.choice_episode);
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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlSafe);
                    rlSafe.setNextFocusRightId(R.id.rl_hygiene);
                    rlSafe.setNextFocusDownId(R.id.rl_pop);
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

    @OnClick({R.id.rl_homepage, R.id.iv_homepage})
    public void onHomePage() {
        startActivity(new Intent(VedioLessonActivity.this, MainActivity.class));
    }

    @OnClick({R.id.iv_safe, R.id.rl_safe})
    public void onSafe() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 1));
    }

    @OnClick({R.id.iv_hygiene, R.id.rl_hygiene})
    public void onHygiene() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 2));
    }

    @OnClick({R.id.iv_nation, R.id.rl_nation})
    public void onNation() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 3));
    }

    @OnClick({R.id.iv_pop, R.id.rl_pop})
    public void onPop() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 4));
    }

    @OnClick({R.id.iv_world, R.id.rl_world})
    public void onWorld() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 5));
    }

    @OnClick({R.id.iv_china, R.id.rl_china})
    public void onChina() {
        startActivity(new Intent(VedioLessonActivity.this, VedioChoiceActivity.class)
                .putExtra(SystemConfig.SEMESTER, mSemesterId)
                .putExtra(SystemConfig.LESSON, 6));
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }

   private ILoadBitmapListener mLoadBitmepListener = new ILoadBitmapListener() {
       @Override
       public void onReady() {
           showLoading(true);
       }

       @Override
       public void onComplete() {
           ivSafe.setImageBitmap(mReceptionLastBitmap);
           ivHygiene.setImageBitmap(mReceptionNextBitmap);
           ivNation.setImageBitmap(mMiddleLastBitmap);
           ivPop.setImageBitmap(mMiddleNextBitmap);
           ivWorld.setImageBitmap(mBigLastBitmap);
           ivChina.setImageBitmap(mBigNextBitmap);
           showLoading(false);
       }
   };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
