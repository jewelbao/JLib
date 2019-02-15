package com.jewel.lib.android.recyclerView;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jewel.lib.android.CompatUtil;
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

    public static void setupHorizontalListView(RecyclerView view, RecyclerView.Adapter adapter, @ColorRes int colorRes) {
        setupListView(view, adapter, true, colorRes, 1);
    }

    public static void setupListView(RecyclerView view, RecyclerView.Adapter adapter, boolean horizontal, @ColorRes int colorRes, int dividerWidth) {
        final Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);

        // 设置间隔
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
}
