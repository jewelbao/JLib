package com.jewel.lib.android.recyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jewel.lib.android.CompatUtil;
import com.jewel.lib.android.recyclerView.decoration.DividerGridItemDecoration;
import com.jewel.lib.android.recyclerView.decoration.HorizontalDividerItemDecoration;
import com.jewel.lib.android.recyclerView.decoration.RecycleViewDivider;
import com.jewel.lib.android.recyclerView.decoration.VerticalDividerItemDecoration;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/04/03
 */
@SuppressWarnings("unused")
public final class RecyclerViewUtil {

    public static void setupListView(RecyclerView view, RecyclerView.Adapter adapter, @ColorRes int colorRes) {
        setupListView(view, adapter, false, colorRes, 1);
    }

    public static void setupListView(RecyclerView view, RecyclerView.Adapter adapter, boolean horizontal, @ColorRes int colorRes, int dividerWidth) {
        final Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);

        Paint paint = new Paint();
        paint.setStrokeWidth(dividerWidth);
        paint.setColor(CompatUtil.getColor(context, colorRes));
        RecycleViewDivider divider;
        if(horizontal) {
            divider = new VerticalDividerItemDecoration.Builder(context).paint(paint).build();
        } else {
            divider = new HorizontalDividerItemDecoration.Builder(context).paint(paint).build();
        }
        view.addItemDecoration(divider);

        view.setAdapter(adapter);
    }

    public static void setupGridView(RecyclerView view, RecyclerView.Adapter adapter, boolean horizontal, int spanCount, int spacing, int lastDividerSize) {
        final Context context = view.getContext();
        GridLayoutManager layoutManager = new GridLayoutManager(context, spanCount);
        layoutManager.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(true);

        view.addItemDecoration(new DividerGridItemDecoration(Color.TRANSPARENT, spacing, spacing, Color.TRANSPARENT, lastDividerSize));
        view.setAdapter(adapter);
    }

    public static void setupHorizontalListView(RecyclerView view, RecyclerView.Adapter adapter, int dividerWidth) {
        final Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        view.setLayoutManager(layoutManager);

        // 设置间隔
        Paint paint = new Paint();
        paint.setStrokeWidth(dividerWidth);
        paint.setColor(CompatUtil.getColor(context, android.R.color.transparent));
        view.addItemDecoration(new VerticalDividerItemDecoration.Builder(context).paint(paint).build());

        view.setAdapter(adapter);
    }

    /**
     * Determine if it is the last column, relative to the direction of the recyclerView
     */
    public static boolean isLastGridColumn(RecyclerView parent, View view) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();
            int childPosition = parent.getChildAdapterPosition(view);
            int spanCountChild = 0;
            for (int i = 0; i <= childPosition; i++) {
                spanCountChild += spanSizeLookUp.getSpanSize(i);
            }
            if (spanCountChild >= gridLayoutManager.getSpanCount() && spanCountChild % gridLayoutManager.getSpanCount() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine whether it is the last line, relative to the direction of the recyclerView,
     * the horizontal direction represents the rightmost side, and the vertical direction represents the bottom line.
     */
    public static boolean isLastGridRaw(RecyclerView parent, View view) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookUp = gridLayoutManager.getSpanSizeLookup();
            int childPosition = parent.getChildAdapterPosition(view);
            int itemCount = gridLayoutManager.getItemCount();
            int spanCountTotal = 0;
            int spanCountChild = 0;
            if (childPosition >= itemCount - gridLayoutManager.getSpanCount()) {
                for (int i = 0; i < itemCount; i++) {
                    spanCountTotal += spanSizeLookUp.getSpanSize(i);
                    if (i <= childPosition) {
                        spanCountChild += spanSizeLookUp.getSpanSize(i);
                    }
                }
                int lastRowCount = spanCountTotal % gridLayoutManager.getSpanCount();
                if (lastRowCount == 0) {
                    lastRowCount = gridLayoutManager.getSpanCount();
                }
                if (spanCountChild > spanCountTotal - lastRowCount) {
                    return true;
                }
            }

        }
        return false;
    }

}
