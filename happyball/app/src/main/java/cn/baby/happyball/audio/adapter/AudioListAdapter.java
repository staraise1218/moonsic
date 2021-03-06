package cn.baby.happyball.audio.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.baby.happyball.R;
import cn.baby.happyball.bean.Audio;

/**
 * @author DRH
 */
public class AudioListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Audio> mAudios = new ArrayList<>();
    private LayoutInflater mInflater;
    private int playingIndex = -1;
    private ISongFouces mSongFouces;
    private boolean isPlaying = false;

    public AudioListAdapter(Context context, List<Audio> audios, ISongFouces songFouces) {
        mContext = context;
        mAudios = audios;
        mInflater = LayoutInflater.from(mContext);
        mSongFouces = songFouces;
    }

    public void setPlayingIndex(int playingIndex) {
        this.playingIndex = playingIndex;
        notifyDataSetChanged();
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        notifyDataSetChanged();
    }

    private void setPlayingTextStatus(ViewHolder viewHolder, int playing_text) {
        viewHolder.audioId.setTextColor(ContextCompat.getColor(mContext, playing_text));
        viewHolder.audioTitle.setTextColor(ContextCompat.getColor(mContext, playing_text));
        viewHolder.audioDuration.setTextColor(ContextCompat.getColor(mContext, playing_text));
        viewHolder.audioSinger.setTextColor(ContextCompat.getColor(mContext, playing_text));
        viewHolder.audioAlbum.setTextColor(ContextCompat.getColor(mContext, playing_text));
    }

    @Override
    public int getCount() {
        return mAudios == null ? 0 : mAudios.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = mInflater.inflate(R.layout.audio_list_item, null);
        ViewHolder viewHolder = new ViewHolder(view);

        Audio audio = mAudios.get(i);
        view.requestFocus();
        viewHolder.audioId.setVisibility(View.VISIBLE);
        viewHolder.audioId.setText(String.valueOf(audio.getId()));
        viewHolder.audioPlay.setVisibility(View.VISIBLE);
        if (playingIndex == i) {
            if (isPlaying) {
                viewHolder.audioPlay.setImageResource(R.mipmap.audio_pause_pressed);
            } else {
                viewHolder.audioPlay.setImageResource(R.mipmap.audio_play_pressed);
            }
            setPlayingTextStatus(viewHolder, R.color.playing_text);
            viewHolder.llAudioListItem.setFocusable(true);
        } else {
            viewHolder.audioPlay.setImageResource(R.mipmap.audio_play_def);
            setPlayingTextStatus(viewHolder, R.color.white);
        }
        viewHolder.audioTitle.setText(audio.getTitle());
        viewHolder.audioDuration.setText(audio.getTimelong());
        viewHolder.audioSinger.setText(audio.getSinger());
        viewHolder.audioAlbum.setText(audio.getAlbum());
        viewHolder.llAudioListItem.setFocusable(true);
        viewHolder.llAudioListItem.setOnClickListener(view1 -> {
            if (mSongFouces != null) {
                mSongFouces.onPlaying(i);
            }
        });
        return view;
    }

    public interface ISongFouces {
        void onPlaying(int position);
    }

    class ViewHolder {
        @BindView(R.id.audio_id)
        TextView audioId;
        @BindView(R.id.audio_play)
        ImageView audioPlay;
        @BindView(R.id.audio_title)
        TextView audioTitle;
        @BindView(R.id.audio_duration)
        TextView audioDuration;
        @BindView(R.id.audio_singer)
        TextView audioSinger;
        @BindView(R.id.audio_album)
        TextView audioAlbum;
        @BindView(R.id.ll_audio_list_item)
        LinearLayout llAudioListItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
