package com.example.mnkj.newobject.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by mnkj on 2017/10/25.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滑动时  
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {  
            // 获取最后一个完全显示的itemPosition  
            int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();  
            int itemCount = manager.getItemCount();  
  
            if (lastItemPosition == (itemCount - 1)) {
                // 加载更多  
                onLoadMore();  
            }  
        }  
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    public abstract void onLoadMore();
}
