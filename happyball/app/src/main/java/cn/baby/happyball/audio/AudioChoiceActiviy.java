package cn.baby.happyball.audio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import cn.baby.happyball.audio.adapter.AudioListAdapter;
import cn.baby.happyball.bean.Audio;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.bean.Lesson;
import cn.baby.happyball.bean.Semester;
import cn.baby.happyball.constant.HttpConstant;
import cn.baby.happyball.constant.SystemConfig;
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

    private static final String LESSON_ID = "lesson_id";

    /**
     * 主页
     */
    @BindView(R.id.rl_homepage)
    RelativeLayout rlHomePage;
    @BindView(R.id.iv_homepage)
    ImageView ivHomePage;

    /**
     * 难度
     */
    @BindView(R.id.iv_difficult)
    ImageView ivDifficult;
    @BindView(R.id.tv_difficult)
    TextView tvDifficult;
    @BindView(R.id.rl_difficult)
    RelativeLayout rlDifficult;

    /**
     * 难度描述
     */
    @BindView(R.id.tv_detail_title)
    TextView tvDetailTitle;
    @BindView(R.id.ll_detail_title)
    LinearLayout llDetailTitle;
    @BindView(R.id.tv_detail_content)
    TextView tvDetailContent;
    @BindView(R.id.rl_difficult_detail)
    RelativeLayout rlDifficultDetail;

    /**
     * 暂停播放
     */
    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.tv_play)
    TextView tvPlay;
    @BindView(R.id.rl_play)
    RelativeLayout rlPlay;

    /**
     * 音频列表
     */
    @BindView(R.id.lv_audio_list)
    ListView lvAudioList;

    private Lesson mLesson;
    private List<Audio> mAudios = new ArrayList<>();
    private AudioListAdapter mAudioListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_choice_activity);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        mLesson = (Lesson) getIntent().getSerializableExtra(SystemConfig.LESSON);

        Map<String, Integer> map = new HashMap<>(2);
        map.put(LESSON_ID, mLesson.getId());
        String json = JSON.toJSONString(map);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpConstant.URL + HttpConstant.AUDIO_LIST)
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
                    mAudios = JSON.parseArray(data, Audio.class);
                    runOnUiThread(() -> initData());
                } catch (Exception e) {
                    runOnUiThread(() -> initData());
                }
            }
        });
    }

    private void initData() {
        String imageUrl = (new StringBuilder().append(HttpConstant.RES_URL).append(mLesson.getImage())).toString();
        loadImage(imageUrl, ivDifficult, R.mipmap.choice_course_semester);

        tvDifficult.setText(mLesson.getName());
        tvDetailContent.setText(mLesson.getDescription());

        lvAudioList.addHeaderView(getLayoutInflater().inflate(R.layout.audio_list_item, null));
        mAudioListAdapter = new AudioListAdapter(getApplicationContext(), mAudios);
        lvAudioList.setAdapter(mAudioListAdapter);
    }

    @OnClick({R.id.iv_homepage, R.id.rl_homepage})
    public void onHomepage() {
        startActivity(new Intent(AudioChoiceActiviy.this, MainActivity.class));
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }
}
