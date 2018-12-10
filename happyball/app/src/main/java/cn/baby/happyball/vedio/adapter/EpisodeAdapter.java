package cn.baby.happyball.vedio.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.baby.happyball.R;
import cn.baby.happyball.bean.Episode;
import cn.baby.happyball.view.recyclerView.TvRecyclerView;

/**
 * @author DRH
 * @data 2018/10/13
 */
public class EpisodeAdapter extends TvRecyclerView.TvAdapter<Episode> {

    private Context mContext;

    public EpisodeAdapter(Context context, List<Episode> data) {
        super(context, data);
        mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder onSetViewHolder(View view) {
        return new EpisodeViewHolder(view);
    }

    @NonNull
    @Override
    protected int onSetItemLayout() {
        return R.layout.episode_recyle_item;
    }

    @Override
    protected void onSetItemData(RecyclerView.ViewHolder viewHolder, int position) {
        final EpisodeViewHolder holder = (EpisodeViewHolder) viewHolder;
        Episode episode = mData.get(position);
        holder.tvEpisodeNum.setText(String.format(mContext.getString(R.string.the_number), episode.getEpisode()));
        holder.tvEpisodeNmae.setText(episode.getTitle());
        holder.itemView.setOnFocusChangeListener(((view, b) -> {
            if (b) {
                holder.ivEpisode.setImageResource(R.mipmap.choice_episode_focus);
//                focusStatus(view, position);
            } else {
                holder.ivEpisode.setImageResource(R.mipmap.choice_episode);
//                normalStatus(view, position);
            }
        }));
    }

    @Override
    protected void onItemFocus(View itemView, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //抬高Z轴
            ViewCompat.animate(itemView).scaleX(1.05f).scaleY(1.05f).translationZ(0.5f).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.05f).scaleY(1.05f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected void onItemGetNormal(View itemView, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).translationZ(0).start();
        } else {
            ViewCompat.animate(itemView).scaleX(1.0f).scaleY(1.0f).start();
            ViewGroup parent = (ViewGroup) itemView.getParent();
            parent.requestLayout();
            parent.invalidate();
        }
    }

    @Override
    protected int getCount() {
        return mData == null ? 0 : mData.size();
    }

    private class EpisodeViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlEpisode;
        ImageView ivEpisode;
        TextView tvEpisodeNum;
        TextView tvEpisodeNmae;

        public EpisodeViewHolder(View itemView) {
            super(itemView);
            rlEpisode = itemView.findViewById(R.id.rl_episode);
            ivEpisode = itemView.findViewById(R.id.iv_episode);
            tvEpisodeNum = itemView.findViewById(R.id.tv_episode_num);
            tvEpisodeNmae = itemView.findViewById(R.id.tv_episode_name);
        }
    }
}
