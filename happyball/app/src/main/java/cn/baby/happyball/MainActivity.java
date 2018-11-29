package cn.baby.happyball;

import android.content.Intent;
import android.os.Bundle;
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
import cn.baby.happyball.audio.AudioLessonActivity;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.VedioLessonActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    List<Semester> mSemesters = new ArrayList<>(6);

    /**
     * 第一次按键事件处理
     */
    boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindEvent();
        getData();
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
        showLoading(true);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HttpConstant.URL);
        if (mMode == 0) {
            tvTitle.setText(R.string.vedio_title);
            stringBuilder.append(HttpConstant.VIDEO_CLASSES);
        } else if (mMode == 1) {
            tvTitle.setText(R.string.audio_title);
            stringBuilder.append(HttpConstant.AUDIO_CLASSES);
        }
        String url = stringBuilder.toString();
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
                    mSemesters = JSON.parseArray(data, Semester.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> showLoading(false));
                }
            }
        });
    }

    private void initData() {
        for (Semester semester : mSemesters) {
            String imageUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(semester.getImage())).toString();
            switch (semester.getId()) {
                case 1:
                    tvBigNext.setText(semester.getName());
                    loadImage(imageUrl, ivBigNext, R.mipmap.ic_main_big_next);
                    break;

                case 2:
                    tvMiddleNext.setText(semester.getName());
                    loadImage(imageUrl, ivMiddleNext, R.mipmap.ic_main_middle_next);
                    break;

                case 3:
                    tvReceptionNext.setText(semester.getName());
                    loadImage(imageUrl, ivReceptionNext, R.mipmap.ic_main_reception_next);
                    break;

                case 4:
                    tvBigLast.setText(semester.getName());
                    loadImage(imageUrl, ivBigLast, R.mipmap.ic_main_big_last);
                    break;

                case 5:
                    tvMiddleLast.setText(semester.getName());
                    loadImage(imageUrl, ivMiddleLast, R.mipmap.ic_main_middle_last);
                    break;

                case 6:
                    tvReceptionLast.setText(semester.getName());
                    loadImage(imageUrl, ivReceptionLast, R.mipmap.ic_main_reception_last);
                    break;

                default:
                    break;
            }
        }
        showLoading(false);

        if (mMode == 0) {
            obtainViewFocus(rlVedio);
            loseViewFocus(rlAudio);
            rlVedio.requestFocus();
            rlVedio.setFocusable(true);
            rlVedio.setNextFocusRightId(R.id.rl_reception_last);
            rlVedio.setNextFocusDownId(R.id.rl_audio);
        } else {
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
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(0)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(0)));
        }
    }

    @OnClick({R.id.iv_reception_next, R.id.rl_reception_next})
    public void onReceptionNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(3)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(3)));
        }
    }

    @OnClick({R.id.iv_middle_last, R.id.rl_middle_last})
    public void onMiddleLastSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(1)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(1)));
        }
    }

    @OnClick({R.id.iv_middle_next, R.id.rl_middle_next})
    public void onMiddleNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(4)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(4)));
        }
    }

    @OnClick({R.id.iv_big_last, R.id.rl_big_last})
    public void onBigLastSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(2)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(2)));
        }
    }

    @OnClick({R.id.iv_big_next, R.id.rl_big_next})
    public void onBigNextSemester() {
        if (mMode == 0) {
            startActivity(new Intent(MainActivity.this, VedioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(5)));
        } else {
            startActivity(new Intent(MainActivity.this, AudioLessonActivity.class)
                    .putExtra(SystemConfig.SEMESTER, mSemesters.get(5)));
        }
    }

    @OnClick(R.id.rl_vedio)
    public void onVedio() {
        if (mMode != 0) {
            mMode = 0;
            mSemesters.clear();
            getData();
        }
    }

    @OnClick(R.id.rl_audio)
    public void onAudio() {
        if (mMode != 1) {
            mMode = 1;
            mSemesters.clear();
            getData();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_vedio:
                if (b && mMode != 0) {
                    mMode = 0;
                    mSemesters.clear();
                    getData();
                }
                break;
            case R.id.rl_audio:
                if (b && mMode != 1) {
                    mMode = 1;
                    mSemesters.clear();
                    getData();
                }
                break;
            case R.id.rl_reception_last:
                receptionLastFocusChange(b);
                break;
            case R.id.rl_middle_last:
                LastFocusChange(b, rlMiddleLast, R.id.rl_reception_last, R.id.rl_big_last, R.id.rl_middle_next);
                break;
            case R.id.rl_big_last:
                LastFocusChange(b, rlBigLast, R.id.rl_middle_last, R.id.rl_reception_next, R.id.rl_big_next);
                break;
            case R.id.rl_reception_next:
                NextFocusChange(b, rlReceptionNext, R.id.rl_big_last, R.id.rl_middle_next, R.id.rl_reception_last);
                break;
            case R.id.rl_middle_next:
                NextFocusChange(b, rlMiddleNext, R.id.rl_reception_next, R.id.rl_big_next, R.id.rl_middle_last);
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

    private void NextFocusChange(boolean b, RelativeLayout rlReceptionNext, int rl_big_last, int rl_middle_next, int rl_reception_last) {
        if (b) {
            obtainViewFocus(rlReceptionNext);
            rlReceptionNext.setNextFocusLeftId(rl_big_last);
            rlReceptionNext.setNextFocusRightId(rl_middle_next);
            rlReceptionNext.setNextFocusUpId(rl_reception_last);
        } else {
            loseViewFocus(rlReceptionNext);
        }
    }

    private void LastFocusChange(boolean b, RelativeLayout rlMiddleLast, int rl_reception_last, int rl_big_last, int rl_middle_next) {
        if (b) {
            obtainViewFocus(rlMiddleLast);
            rlMiddleLast.setNextFocusLeftId(rl_reception_last);
            rlMiddleLast.setNextFocusRightId(rl_big_last);
            rlMiddleLast.setNextFocusDownId(rl_middle_next);
        } else {
            loseViewFocus(rlMiddleLast);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (event.getAction() == KeyEvent.ACTION_DOWN && isFirst) {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN && isFirst) {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN && isFirst) {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN && isFirst) {
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
                if (event.getAction() == KeyEvent.ACTION_DOWN && isFirst) {
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
}
