package com.jewel.lib.android.recyclerView.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jewel.lib.android.recyclerView.RecyclerViewUtil;

/**
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/2/11
 */
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    private int mDividerHSpacing;

    private int mDividerVSpacing;

    private Drawable mLastDivider;

    private int mLastDividerSize;

    public DividerGridItemDecoration() {
        set(new ColorDrawable(Color.TRANSPARENT), mDividerHSpacing, mDividerVSpacing, new ColorDrawable(Color.TRANSPARENT), mLastDividerSize);
    }

    public DividerGridItemDecoration(int horizaontalSpacing, int verticalSpacing, int lastDividerSize) {
        set(new ColorDrawable(Color.TRANSPARENT), horizaontalSpacing, verticalSpacing, new ColorDrawable(Color.TRANSPARENT), lastDividerSize);
    }

    public DividerGridItemDecoration(Drawable divider, int horizaontalSpacing, int verticalSpacing, Drawable lastDivider, int lastDividerSize) {
        set(divider, horizaontalSpacing, verticalSpacing, lastDivider, lastDividerSize);
    }

    public DividerGridItemDecoration(@ColorInt int dividerColor, int horizaontalSpacing, int verticalSpacing, @ColorInt int lastDividerColor, int lastDividerSize) {
        set(new ColorDrawable(dividerColor), horizaontalSpacing, verticalSpacing, new ColorDrawable(lastDividerColor), lastDividerSize);
    }

    /**
     * @param lastDividerSize The trailing distance of the last line, greater than 0 will take effect
     */
    private void set(Drawable divider, int horizontalSpacing, int verticalSpacing, Drawable lastDivider, int lastDividerSize) {
        this.mDivider = divider;
        this.mDividerHSpacing = horizontalSpacing;
        this.mDividerVSpacing = verticalSpacing;
        this.mLastDivider = lastDivider;
        this.mLastDividerSize = lastDividerSize;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mDivider == null) {
            return;
        }
        if (mDividerHSpacing > 0) {
            drawHorizontalSpacing(c, parent);
        }
        if (mDividerVSpacing > 0) {
            drawVerticalSpacing(c, parent);
        }
        if (mLastDividerSize > 0) {
            drawLastSpacing(c, parent);
        }
    }

    /**
     * Draw horizontal spacing, excluding the last column
     */
    private void drawHorizontalSpacing(Canvas c, RecyclerView parent) {
        if (isGridLayoutManager(parent)) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                if (i < childCount - ((GridLayoutManager) parent.getLayoutManager()).getSpanCount()
                        || (parent.getLayoutManager().canScrollVertically() || !RecyclerViewUtil.isLastGridRaw(parent, child))) {
                    drawHorizontal(c, child, mDivider, mDividerHSpacing);
                }
            }
        }
    }

    private void drawHorizontal(Canvas c, View child, Drawable divider, int spacing) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int left = child.getRight() + params.rightMargin;
        final int right = left + spacing;
        final int top = child.getTop() - params.topMargin;
        final int bottom = child.getBottom() + params.bottomMargin;
        divider.setBounds(left, top, right, bottom);
        divider.draw(c);
    }

    /**
     * Draw vertical spacing, not including the last line
     */
    private void drawVerticalSpacing(Canvas c, RecyclerView parent) {
        if (isGridLayoutManager(parent)) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                if (i < childCount - ((GridLayoutManager) parent.getLayoutManager()).getSpanCount()
                        || (parent.getLayoutManager().canScrollHorizontally() || !RecyclerViewUtil.isLastGridRaw(parent, child))) {
                    drawVertical(c, child, mDivider, mDividerVSpacing);
                }
            }
        }
    }

    private void drawVertical(Canvas c, View child, Drawable divider, int spacing) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        final int left = child.getLeft() - params.leftMargin;
        final int right = child.getRight() + params.rightMargin;
        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + spacing;
        divider.setBounds(left, top, right, bottom);
        divider.draw(c);
    }

    /**
     * Draw the last line
     */
    private void drawLastSpacing(Canvas c, RecyclerView parent) {
        if (isGridLayoutManager(parent)) {
            int childCount = parent.getChildCount();
            int i = childCount - ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            for (; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                if (RecyclerViewUtil.isLastGridRaw(parent, child)) {
                    if (parent.getLayoutManager().canScrollHorizontally()) {
                        drawHorizontal(c, child, mLastDivider, mLastDividerSize);
                    } else if (parent.getLayoutManager().canScrollVertically()) {
                        drawVertical(c, child, mLastDivider, mLastDividerSize);
                    }
                }
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (!isGridLayoutManager(parent)) {
            return;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        if (gridLayoutManager != null) {
            if (gridLayoutManager.canScrollHorizontally()) {
                if (RecyclerViewUtil.isLastGridRaw(parent, view)) {
                    if (parent.getChildAdapterPosition(view) == gridLayoutManager.getItemCount() - 1) {
                        outRect.set(0, 0, mLastDividerSize, 0);
                    } else {
                        outRect.set(0, 0, mLastDividerSize, mDividerVSpacing);
                    }
                } else {
                    outRect.set(0, 0, mDividerHSpacing, mDividerVSpacing);
                }
            } else if (gridLayoutManager.canScrollVertically()) {
                if (RecyclerViewUtil.isLastGridRaw(parent, view)) {
                    if (parent.getChildAdapterPosition(view) == gridLayoutManager.getItemCount() - 1) {
                        outRect.set(0, 0, 0, mLastDividerSize);
                    } else {
                        outRect.set(0, 0, mDividerHSpacing, mLastDividerSize);
                    }
                } else {
                    outRect.set(0, 0, mDividerHSpacing, mDividerVSpacing);
                }
            }
        }
    }

    private boolean isGridLayoutManager(RecyclerView parent) {
        return parent.getLayoutManager() != null && parent.getLayoutManager() instanceof GridLayoutManager;
    }

}
