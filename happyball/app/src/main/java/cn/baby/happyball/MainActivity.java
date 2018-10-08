package cn.baby.happyball;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.vedio.VedioChoiceActiviy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnFocusChangeListener {

    @BindView(R.id.rl_vedio)
    RelativeLayout rlVedio;
    @BindView(R.id.iv_vedio)
    ImageView ivVedio;
    @BindView(R.id.rl_audio)
    RelativeLayout rlAudio;
    @BindView(R.id.iv_audio)
    ImageView ivAudio;
    @BindView(R.id.rl_reception_last_semester)
    RelativeLayout rlReceptionLast;
    @BindView(R.id.iv_reception_last_semester)
    ImageView ivReceptionLast;
    @BindView(R.id.tv_receptin_last_semester_name)
    TextView tvReceptionLast;
    @BindView(R.id.rl_reception_next_semester)
    RelativeLayout rlReceptionNext;
    @BindView(R.id.iv_reception_next_semester)
    ImageView ivReceptionNext;
    @BindView(R.id.tv_receptin_next_semester_name)
    TextView tvReceptionNext;
    @BindView(R.id.rl_middle_last_semester)
    RelativeLayout rlMiddleLast;
    @BindView(R.id.iv_middle_last_semester)
    ImageView ivMiddleLast;
    @BindView(R.id.tv_middle_last_semester_name)
    TextView tvMiddleLast;
    @BindView(R.id.rl_middle_next_semester)
    RelativeLayout rlMiddleNext;
    @BindView(R.id.iv_middle_next_semester)
    ImageView ivMiddleNext;
    @BindView(R.id.tv_middle_next_semester_name)
    TextView tvMiddleNext;
    @BindView(R.id.rl_big_last_semester)
    RelativeLayout rlBigLast;
    @BindView(R.id.iv_big_last_semester)
    ImageView ivBigLast;
    @BindView(R.id.tv_big_last_semester_name)
    TextView tvBigLast;
    @BindView(R.id.rl_big_next_semester)
    RelativeLayout rlBigNext;
    @BindView(R.id.iv_big_next_semester)
    ImageView ivBigNext;
    @BindView(R.id.tv_big_next_semester_name)
    TextView tvBigNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bindEvent();
        getData();
    }

    public void bindEvent() {
        rlVedio.setFocusable(true);
        rlVedio.setOnFocusChangeListener(this);
        rlAudio.setOnFocusChangeListener(this);
        ivReceptionLast.setOnFocusChangeListener(this);
        ivReceptionNext.setOnFocusChangeListener(this);
        ivMiddleLast.setOnFocusChangeListener(this);
        ivMiddleNext.setOnFocusChangeListener(this);
        ivBigLast.setOnFocusChangeListener(this);
        ivBigNext.setOnFocusChangeListener(this);
    }

    public void getData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.CLASSES)
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
            }
        });
    }

    @OnClick(R.id.iv_reception_last_semester)
    public void onReceptionLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick(R.id.iv_reception_next_semester)
    public void onReceptionNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick(R.id.iv_middle_last_semester)
    public void onMiddleLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick(R.id.iv_middle_next_semester)
    public void onMiddleNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick(R.id.iv_big_last_semester)
    public void onBigLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @OnClick(R.id.iv_big_next_semester)
    public void onBigNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioChoiceActiviy.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()) {
            case R.id.rl_vedio:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_audio:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_reception_last_semester:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_reception_next_semester:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_middle_last_semester:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_middle_next_semester:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_big_last_semester:
                if (b) {
                } else {
                }
                break;

            case R.id.iv_big_next_semester:
                if (b) {
                } else {
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            //模拟器测试时键盘中的的Enter键，模拟ok键（推荐TV开发中使用蓝叠模拟器）
            case KeyEvent.KEYCODE_ENTER:
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:

                break;

            case KeyEvent.KEYCODE_DPAD_UP:

                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
