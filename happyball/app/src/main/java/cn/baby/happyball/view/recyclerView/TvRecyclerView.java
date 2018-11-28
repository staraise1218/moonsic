package cn.baby.happyball.view.recyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author DRH
 * @data 2018/10/13
 * <p>
 * 1.响应五向键，上下左右会跟着移动，并获得焦点，在获得焦点时会抬高
 * 2.在鼠标hover在条目上时会获得焦点。
 * 3.添加了条目的点击和长按事件
 * 4.添加了是否第一个可见条目和是否是最后一个可见条目的方法
 * 5.在item获得焦点时和失去焦点时，这里有相应的回调方法。
 */
public class TvRecyclerView extends RecyclerView {

    public TvRecyclerView(Context context) {
        super(context);
    }

    public TvRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TvRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //在recyclerView的move事件情况下，拦截调，只让它响应五向键和左右箭头移动
        return ev.getAction() == MotionEvent.ACTION_MOVE || super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int dx = this.getChildAt(0).getWidth();
        View focusView = this.getFocusedChild();
        if (focusView != null) {
            //处理左右方向键移动Item到边之后RecyclerView跟着移动
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return true;
                } else {
                    View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                    if (rightView != null) {
                        rightView.requestFocusFromTouch();
                        return true;
                    } else {
                        this.smoothScrollBy(dx, 0);
                        //移动之后获得焦点，是在scroll方法中处理的。
                        return true;
                    }
                }
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                View leftView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    return true;
                } else {
                    if (leftView != null) {
                        leftView.requestFocusFromTouch();
                        return true;
                    } else {
                        this.smoothScrollBy(-dx, 0);
                        return true;
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        //响应五向键，在Scroll时去获得下一个焦点
        final View focusView = this.getFocusedChild();
        if (focusView != null) {
            if (dx > 0) {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_RIGHT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            } else {
                View rightView = FocusFinder.getInstance().findNextFocus(this, focusView, View.FOCUS_LEFT);
                if (rightView != null) {
                    rightView.requestFocusFromTouch();
                }
            }
        }

    }


    /**
     * 第一个条目是否可见
     *
     * @return 可见返回true，不可见返回false
     */
    public boolean isFirstItemVisible() {
        return getFirstPosition() == 0;
    }

    /**
     * 第一个条目可见条目的position
     *
     * @return 可见返回true，不可见返回false
     */
    public int getFirstPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) layoutManager).
                    findFirstCompletelyVisibleItemPositions(firstVisibleItems);
            return firstVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        }
        return -1;
    }


    /**
     * 最后一个条目是否可见
     *
     * @param lineNum    行数
     * @param allItemNum item总数
     * @return 可见返回true，不可见返回false
     */
    public boolean isLastItemVisible(int lineNum, int allItemNum) {
        int position = getLastPosition();
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            boolean isVisible = position >= (allItemNum - lineNum);
//            if (isVisible) {
//                scrollBy(1, 0);
//            }
            return isVisible;
        } else if (layoutManager instanceof LinearLayoutManager) {
            return allItemNum - 1 == position;
        }
        return false;
    }


    /**
     * 最后一个条目可见条目的position
     *
     * @return 可见返回true，不可见返回false
     */
    public int getLastPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItems = null;
            lastVisibleItems = ((StaggeredGridLayoutManager) layoutManager).findLastCompletelyVisibleItemPositions(lastVisibleItems);
            return lastVisibleItems[0];
        } else if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        return -1;
    }


    /**
     * 自定义的RecyclerView Adapter，他实现了hover获得焦点，放大的效果。
     * 实现了点击事件和长按点击事件。
     *
     * @param <T>
     */
    public static abstract class TvAdapter<T> extends Adapter<ViewHolder> {
        private LayoutInflater mInflater;
        protected List<T> mData;
        protected Context mContext;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }


        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }


        public TvAdapter(Context context, List<T> data) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(onSetItemLayout(), parent, false);
            return onSetViewHolder(view);
        }

        protected abstract ViewHolder onSetViewHolder(View view);

        /**
         * 设置item的layout
         *
         * @return item对应的layout
         */
        protected abstract
        @NonNull
        int onSetItemLayout();

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            onSetItemData(holder, position);
            //item可以获得焦点，需要设置这个属性。
            holder.itemView.setFocusable(true);
            holder.itemView.setOnHoverListener((v, event) -> {
                int what = event.getAction();
                switch (what) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        RecyclerView recyclerView = (RecyclerView) holder.itemView.getParent();
                        int[] location = new int[2];
                        recyclerView.getLocationOnScreen(location);
                        int x = location[0];
                        //为了防止滚动冲突，在滚动时候，获取焦点为了显示全，会回滚，这样会导致滚动停止
                        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                            //当超出RecyclerView的边缘时不去响应滚动
                            if (event.getRawX() > recyclerView.getWidth() + x || event.getRawX() < x) {
                                return true;
                            }
                            //鼠标进入view，争取到焦点
                            v.requestFocusFromTouch();
                            v.requestFocus();
                            focusStatus(v, position);
                        }
                        break;
                    //鼠标在view上移动
                    case MotionEvent.ACTION_HOVER_MOVE:
                        break;
                    //鼠标离开view
                    case MotionEvent.ACTION_HOVER_EXIT:
                        normalStatus(v, position);
                        break;
                    default:
                        break;
                }
                return false;
            });

            if (mListener != null) {
                holder.itemView.setOnClickListener(view -> mListener.onItemClick(view, position) );

                holder.itemView.setOnLongClickListener(view -> {
                        mListener.onItemLongClick(view, position);
                        return true;
                });
            }
        }

        /**
         * 为Item的内容设置数据
         *
         * @param viewHolder viewHolder
         * @param position   位置
         */
        protected abstract void onSetItemData(ViewHolder viewHolder, int position);

        /**
         * item获得焦点时调用
         *
         * @param itemView view
         * @param position
         */
        public void focusStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }
            onItemFocus(itemView, position);
        }

        /**
         * 当item获得焦点时处理
         *
         * @param itemView itemView
         * @param position
         */
        protected abstract void onItemFocus(View itemView, int position);


        /**
         * item失去焦点时
         *
         * @param itemView item对应的View
         * @param position
         */
        public void normalStatus(View itemView, int position) {
            if (itemView == null) {
                return;
            }


            onItemGetNormal(itemView, position);

        }

        /**
         * 当条目失去焦点时调用
         *
         * @param itemView 条目对应的View
         */
        protected abstract void onItemGetNormal(View itemView, int position);

        @Override
        public int getItemCount() {
            if (mData != null) {
                return getCount();
            } else {
                return 0;
            }
        }

        protected abstract int getCount();
    }
}
