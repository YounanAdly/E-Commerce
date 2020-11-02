package com.example.android.e_commerce.ui.Adapter;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {


    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    LinearLayoutManager mLayoutManager;


    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) { //check for scroll down
            visibleItemCount = mLayoutManager.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                    loading = false;
                    Log.d("TEST", "LastItem----------");
                    //fetch new data
                    onLoadMore();
                    loading = true;
                }
            }
        }
    }

    public abstract void onLoadMore();
}