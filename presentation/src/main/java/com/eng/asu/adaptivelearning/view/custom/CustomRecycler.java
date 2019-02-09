package com.eng.asu.adaptivelearning.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecycler extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    CustomRecycler.this.setVisibility(View.GONE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    CustomRecycler.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public CustomRecycler(@NonNull Context context) {
        super(context);
    }

    public CustomRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDividerDrawable(@DrawableRes int dividerDrawable, int direction) {

        if (this.getItemDecorationCount() > 0)
            this.removeItemDecorationAt(0);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), direction);
        itemDecoration.setDrawable(getResources().getDrawable(dividerDrawable));
        this.addItemDecoration(itemDecoration);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
