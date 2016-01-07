package com.example.zgq.actionbartest.myview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

public class RefreshLayout extends SwipeRefreshLayout {

    private final int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;

    private float firstTouchY;
    private float lastTouchY;

    private boolean isLoading = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mTouchSlop = 120;
    }

    //set the child view of RefreshLayout,ListView
    public void setChildView(ListView mListView) {
        this.mListView = mListView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        if (isBottom() && !isLoading) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    firstTouchY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:

                    Log.d("-", "1");
                    break;
                case MotionEvent.ACTION_UP:
                    lastTouchY = event.getRawY();
                    if (canLoadMore()) {
                        loadData();
                        Log.d("--", "2");
                    }
//                    return true;
                    break;
//                default:
//                    break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    private boolean canLoadMore() {
        Log.d("---","3");
        return isPullingUp();
    }

    private boolean isBottom() {
        if (mListView.getCount() > 0 || mListView.getCount() == 0) {
            Log.d("----","4");
            if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() -1 &&
                    mListView.getChildAt(mListView.getChildCount() - 1).getBottom() <= mListView.getHeight()) {
                Log.d("-------","7");
                return true;
            }
        }
        return false;
    }

    private boolean isPullingUp() {
        Log.d("-----","5");
        return (firstTouchY - lastTouchY) >= mTouchSlop;
    }

    private void loadData() {
        if (mOnLoadListener != null) {
            Log.d("------","6");
            setLoading(true);
        }
    }

    public void setLoading(boolean loading) {
        if (mListView == null) return;
        isLoading = loading;
        if (loading) {
            if (isRefreshing()) {
                setRefreshing(false);
            }
            mListView.setSelection(mListView.getAdapter().getCount() - 1);
//            mListView.setSelection(0);
            mOnLoadListener.onLoad();
        } else {
            firstTouchY = 0;
            lastTouchY = 0;
        }
        isLoading = false;
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        public void onLoad();
    }
}
