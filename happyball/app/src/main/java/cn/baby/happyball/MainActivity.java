package cn.baby.happyball;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
import cn.baby.happyball.vedio.VedioCurriculumActivity;
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

    /** 0:视频 1:音频 **/
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
            String imageUrl = HttpConstant.IMGURL + semester.getImage();
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

                case 6 :
                    tvReceptionLast.setText(semester.getName());
                    Picasso.with(getApplicationContext()).load(imageUrl).into(ivReceptionLast);
                    break;

                    default:break;
            }
        }
    }

    @OnClick({R.id.iv_reception_last, R.id.rl_reception_last})
    public void onReceptionLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 6));
    }

    @OnClick({R.id.iv_reception_next, R.id.rl_reception_next})
    public void onReceptionNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 3));
    }

    @OnClick({R.id.iv_middle_last, R.id.rl_middle_last})
    public void onMiddleLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 5));
    }

    @OnClick({R.id.iv_middle_next, R.id.rl_middle_next})
    public void onMiddleNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 2));
    }

    @OnClick({R.id.iv_big_last, R.id.rl_big_last})
    public void onBigLastSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 4));
    }

    @OnClick({R.id.iv_big_next, R.id.rl_big_next})
    public void onBigNextSemester() {
        startActivity(new Intent(MainActivity.this, VedioCurriculumActivity.class)
                .putExtra(SystemConfig.SEMESTER, 1));
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

            case R.id.rl_reception_last:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_reception_next:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_middle_last:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_middle_next:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_big_last:
                if (b) {
                } else {
                }
                break;

            case R.id.rl_big_next:
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
