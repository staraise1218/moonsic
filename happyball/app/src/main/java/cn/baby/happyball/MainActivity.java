package cn.baby.happyball;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.audio.AudioChoiceActiviy;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.VedioLessonActivity;

/**
 * @author DRH
 */
public class MainActivity extends BaseActivity implements View.OnFocusChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    /**
     * 视频
     */
    @BindView(R.id.rl_vedio)
    RelativeLayout rlVedio;
    @BindView(R.id.iv_vedio)
    ImageView ivVedio;

    /**
     * 音频
     */
    @BindView(R.id.rl_audio)
    RelativeLayout rlAudio;
    @BindView(R.id.iv_audio)
    ImageView ivAudio;

    /**
     * 小班上学期
     */
    @BindView(R.id.rl_reception_last)
    RelativeLayout rlReceptionLast;
    @BindView(R.id.iv_reception_last)
    ImageView ivReceptionLast;
    @BindView(R.id.tv_reception_last_name)
    TextView tvReceptionLast;

    /**
     * 小班下学期
     */
    @BindView(R.id.rl_reception_next)
    RelativeLayout rlReceptionNext;
    @BindView(R.id.iv_reception_next)
    ImageView ivReceptionNext;
    @BindView(R.id.tv_reception_next_name)
    TextView tvReceptionNext;

    /**
     * 中班上学期
     */
    @BindView(R.id.rl_middle_last)
    RelativeLayout rlMiddleLast;
    @BindView(R.id.iv_middle_last)
    ImageView ivMiddleLast;
    @BindView(R.id.tv_middle_last_name)
    TextView tvMiddleLast;

    /**
     * 中班下学期
     */
    @BindView(R.id.rl_middle_next)
    RelativeLayout rlMiddleNext;
    @BindView(R.id.iv_middle_next)
    ImageView ivMiddleNext;
    @BindView(R.id.tv_middle_next_name)
    TextView tvMiddleNext;

    /**
     * 大班上学期
     */
    @BindView(R.id.rl_big_last)
    RelativeLayout rlBigLast;
    @BindView(R.id.iv_big_last)
    ImageView ivBigLast;
    @BindView(R.id.tv_big_last_name)
    TextView tvBigLast;

    /**
     * 大班下学期
     */
    @BindView(R.id.rl_big_next)
    RelativeLayout rlBigNext;
    @BindView(R.id.iv_big_next)
    ImageView ivBigNext;
    @BindView(R.id.tv_big_next_name)
    TextView tvBigNext;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    /**
     * 0:视频 1:音频
     **/
    private int mMode = 0;

    /**
     * 第一次按键事件处理
     */
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        bindEvent();
        getData();
        initData();
    }

    public void bindEvent() {
        rlVedio.setOnFocusChangeListener(this);
        rlAudio.setOnFocusChangeListener(this);
        rlReceptionLast.setOnFocusChangeListener(this);
        rlReceptionNext.setOnFocusChangeListener(this);
        rlMiddleLast.setOnFocusChangeListener(this);
        rlMiddleNext.setOnFocusChangeListener(this);
        rlBigLast.setOnFocusChangeListener(this);
        rlBigNext.setOnFocusChangeListener(this);
    }

    public void getData() {
        new LoadBitmapAsyncTask(mLoadBitmapListener).execute();
    }

    private void initData() {
        if (mMode == 0) {
            tvTitle.setText(R.string.vedio_title);
            obtainViewFocus(rlVedio);
            loseViewFocus(rlAudio);
            rlVedio.requestFocus();
            rlVedio.setFocusable(true);
            rlVedio.setNextFocusRightId(R.id.rl_reception_last);
            rlVedio.setNextFocusDownId(R.id.rl_audio);
        } else {
            tvTitle.setText(R.string.audio_title);
            obtainViewFocus(rlAudio);
            loseViewFocus(rlVedio);
            rlAudio.requestFocus();
            rlAudio.setFocusable(true);
            rlAudio.setNextFocusRightId(R.id.rl_reception_last);
            rlAudio.setNextFocusUpId(R.id.rl_vedio);
        }
    }

    @OnClick({R.id.iv_reception_last, R.id.rl_reception_last})
    public void onReceptionLastSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 1));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 1));
        }
    }

    @OnClick({R.id.iv_reception_next, R.id.rl_reception_next})
    public void onReceptionNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 4));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 4));
        }
    }

    @OnClick({R.id.iv_middle_last, R.id.rl_middle_last})
    public void onMiddleLastSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 2));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 2));
        }
    }

    @OnClick({R.id.iv_middle_next, R.id.rl_middle_next})
    public void onMiddleNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 5));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 5));
        }
    }

    @OnClick({R.id.iv_big_last, R.id.rl_big_last})
    public void onBigLastSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 3));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 3));
        }
    }

    @OnClick({R.id.iv_big_next, R.id.rl_big_next})
    public void onBigNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, 6));
        } else {
            startActivity(new Intent(MainActivity.this, AudioChoiceActiviy.class)
                    .putExtra(SystemConfig.LESSON, 6));
        }
    }

    @OnClick(R.id.rl_vedio)
    public void onVedio() {
        if (mMode != 0) {
            mMode = 0;
            initData();
        }
    }

    @OnClick(R.id.rl_audio)
    public void onAudio() {
        if (mMode != 1) {
            mMode = 1;
            initData();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_vedio:
                if (b && mMode != 0) {
                    mMode = 0;
                    initData();
                }
                break;
            case R.id.rl_audio:
                if (b && mMode != 1) {
                    mMode = 1;
                    initData();
                }
                break;
            case R.id.rl_reception_last:
                receptionLastFocusChange(b);
                break;
            case R.id.rl_middle_last:
                lastFocusChange(b, rlMiddleLast, R.id.rl_reception_last, R.id.rl_big_last, R.id.rl_middle_next);
                break;
            case R.id.rl_big_last:
                lastFocusChange(b, rlBigLast, R.id.rl_middle_last, R.id.rl_reception_next, R.id.rl_big_next);
                break;
            case R.id.rl_reception_next:
                nextFocusChange(b, rlReceptionNext, R.id.rl_big_last, R.id.rl_middle_next, R.id.rl_reception_last);
                break;
            case R.id.rl_middle_next:
                nextFocusChange(b, rlMiddleNext, R.id.rl_reception_next, R.id.rl_big_next, R.id.rl_middle_last);
                break;
            case R.id.rl_big_next:
                bigNextFocusChange(b);
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

    private void bigNextFocusChange(boolean b) {
        if (b) {
            obtainViewFocus(rlBigNext);
            rlBigNext.setNextFocusLeftId(R.id.rl_middle_next);
            rlBigNext.setNextFocusUpId(R.id.rl_big_last);
        } else {
            loseViewFocus(rlBigNext);
        }
    }

    private void nextFocusChange(boolean b, RelativeLayout rl, int rlLeftId, int rlRightId, int rlUpId) {
        if (b) {
            obtainViewFocus(rl);
            rl.setNextFocusLeftId(rlLeftId);
            rl.setNextFocusRightId(rlRightId);
            rl.setNextFocusUpId(rlUpId);
        } else {
            loseViewFocus(rl);
        }
    }

    private void lastFocusChange(boolean b, RelativeLayout rl, int rlLeftId, int rlRightId, int rlNextId) {
        if (b) {
            obtainViewFocus(rl);
            rl.setNextFocusLeftId(rlLeftId);
            rl.setNextFocusRightId(rlRightId);
            rl.setNextFocusDownId(rlNextId);
        } else {
            loseViewFocus(rl);
        }
    }

    private void receptionLastFocusChange(boolean b) {
        if (b) {
            obtainViewFocus(rlReceptionLast);
            if (mMode == 0) {
                rlReceptionLast.setNextFocusLeftId(R.id.rl_vedio);
            } else {
                rlReceptionLast.setNextFocusLeftId(R.id.rl_audio);
            }
            rlReceptionLast.setNextFocusRightId(R.id.rl_middle_last);
            rlReceptionLast.setNextFocusDownId(R.id.rl_reception_next);
        } else {
            loseViewFocus(rlReceptionLast);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlVedio);
                    loseViewFocus(rlAudio);
                    rlVedio.requestFocus();
                    rlVedio.setFocusable(true);
                    rlVedio.setNextFocusRightId(R.id.rl_reception_last);
                    rlVedio.setNextFocusDownId(R.id.rl_audio);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlVedio);
                    loseViewFocus(rlAudio);
                    rlVedio.requestFocus();
                    rlVedio.setFocusable(true);
                    rlVedio.setNextFocusRightId(R.id.rl_reception_last);
                    rlVedio.setNextFocusDownId(R.id.rl_audio);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlVedio);
                    loseViewFocus(rlAudio);
                    rlVedio.requestFocus();
                    rlVedio.setFocusable(true);
                    rlVedio.setNextFocusRightId(R.id.rl_reception_last);
                    rlVedio.setNextFocusDownId(R.id.rl_audio);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlVedio);
                    loseViewFocus(rlAudio);
                    rlVedio.requestFocus();
                    rlVedio.setFocusable(true);
                    rlVedio.setNextFocusRightId(R.id.rl_reception_last);
                    rlVedio.setNextFocusDownId(R.id.rl_audio);
                    isFirst = false;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (event.getAction() == KeyEvent.ACTION_UP && isFirst) {
                    obtainViewFocus(rlVedio);
                    loseViewFocus(rlAudio);
                    rlVedio.requestFocus();
                    rlVedio.setFocusable(true);
                    rlVedio.setNextFocusRightId(R.id.rl_reception_last);
                    rlVedio.setNextFocusDownId(R.id.rl_audio);
                    isFirst = false;
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

    private ILoadBitmapListener mLoadBitmapListener = new ILoadBitmapListener() {
        @Override
        public void onReady() {
            showLoading(true);
        }

        @Override
        public void onComplete() {
            ivReceptionLast.setImageBitmap(mReceptionLastBitmap);
            ivReceptionNext.setImageBitmap(mReceptionNextBitmap);
            ivMiddleLast.setImageBitmap(mMiddleLastBitmap);
            ivMiddleNext.setImageBitmap(mMiddleNextBitmap);
            ivBigLast.setImageBitmap(mBigLastBitmap);
            ivBigNext.setImageBitmap(mBigNextBitmap);
            showLoading(false);
        }
    };
}
