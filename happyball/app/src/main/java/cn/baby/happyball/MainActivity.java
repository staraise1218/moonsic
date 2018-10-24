package cn.baby.happyball;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
            stringBuilder.append(HttpConstant.VIDEO_CLASSES);
        } else if (mMode == 1) {
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                try {
                    String data = (new JSONObject(responseStr)).optString("data");
                    mSemesters = JSON.parseArray(data, Semester.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
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
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivBigNext);
                    break;

                case 2:
                    tvMiddleNext.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivMiddleNext);
                    break;

                case 3:
                    tvReceptionNext.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivReceptionNext);
                    break;

                case 4:
                    tvBigLast.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivBigLast);
                    break;

                case 5:
                    tvMiddleLast.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivMiddleLast);
                    break;

                case 6:
                    tvReceptionLast.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivReceptionLast);
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
        } else {
            obtainViewFocus(rlAudio);
            loseViewFocus(rlVedio);
            rlAudio.requestFocus();
            rlAudio.setFocusable(true);
        }
    }

    @OnClick({R.id.iv_reception_last, R.id.rl_reception_last})
    public void onReceptionLastSemester() {
//        switchFocusView(0);
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
//        switchFocusView(3);
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
//        switchFocusView(1);
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
//        switchFocusView(4);
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
//        switchFocusView(2);
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
//        switchFocusView(5);
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
            getData();
        }
    }

    @OnClick(R.id.rl_audio)
    public void onAudio() {
        if (mMode != 1) {
            mMode = 1;
            getData();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_vedio:
                if (b) {
                    mMode = 0;
                    getData();
                }
                break;

            case R.id.rl_audio:
                if (b) {
                    mMode = 1;
                    getData();
                }
                break;

            case R.id.rl_reception_last:
                if (b) {
                    obtainViewFocus(rlReceptionLast);
                    rlReceptionLast.requestFocus();
                    rlReceptionLast.setFocusable(true);
                    if (mMode == 0) {
                        ivReceptionLast.setNextFocusRightId(R.id.rl_vedio);
                    } else {
                        ivReceptionLast.setNextFocusRightId(R.id.rl_audio);
                    }
                } else {
                    loseViewFocus(rlReceptionLast);
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

    private void switchFocusView(int index) {
        switch (index) {
            case 0:
                obtainViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
            case 3:
                loseViewFocus(rlReceptionLast);
                obtainViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
            case 1:
                loseViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                obtainViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
            case 4:
                loseViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                obtainViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
            case 2:
                loseViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                obtainViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
            case 5:
                loseViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                obtainViewFocus(rlBigNext);
                break;
            default:
                loseViewFocus(rlReceptionLast);
                loseViewFocus(rlReceptionNext);
                loseViewFocus(rlMiddleLast);
                loseViewFocus(rlMiddleNext);
                loseViewFocus(rlBigLast);
                loseViewFocus(rlBigNext);
                break;
        }
    }

    public void showLoading(boolean show) {
        pbLoading.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
