package com.jewel.lib.android.recyclerView.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 自定义RecyclerView的Divider
 * @author Kevin
 * @version 1.0
 * @since 2015/9/25
 */
@SuppressWarnings("ALL")
abstract class RecycleViewDivider extends RecyclerView.ItemDecoration{

	private static final int DEFAULT_SIZE = 2;
	private static final int[] ATTRS = new int[] {android.R.attr.listDivider};

	protected enum DividerType {
		DRAWABLE, PAINT, COLOR
	}

	DividerType mDividerType = DividerType.DRAWABLE;
	private VisibilityProvider mVisibilityProvider;
	PaintProvider mPaintProvider;
	private ColorProvider mColorProvider;
	DrawableProvider mDrawableProvider;
	SizeProvider mSizeProvider;
	private boolean mShowLastDivider;
	private Paint mPaint;

	RecycleViewDivider(Builder builder) {
		if (builder.mPaintProvider != null) {
			mDividerType = DividerType.PAINT;
			mPaintProvider = builder.mPaintProvider;
		} else if (builder.mColorProvider != null) {
			mDividerType = DividerType.COLOR;
			mColorProvider = builder.mColorProvider;
			mPaint = new Paint();
			setSizeProvider(builder);
		} else {
			mDividerType = DividerType.DRAWABLE;
			if (builder.mDrawableProvider == null) {
				TypedArray a = builder.mContext.obtainStyledAttributes(ATTRS);
				final Drawable divider = a.getDrawable(0);
				a.recycle();
				mDrawableProvider = new DrawableProvider() {
					@Override
					public Drawable drawableProvider(int position, RecyclerView parent) {
						return divider;
					}
				};
			} else {
				mDrawableProvider = builder.mDrawableProvider;
			}
			mSizeProvider = builder.mSizeProvider;
		}

		mVisibilityProvider = builder.mVisibilityProvider;
		mShowLastDivider = builder.mShowLastDivider;
	}

	private void setSizeProvider(Builder builder) {
		mSizeProvider = builder.mSizeProvider;
		if (mSizeProvider == null) {
			mSizeProvider = new SizeProvider() {
				@Override
				public int dividerSize(int position, RecyclerView parent) {
					return DEFAULT_SIZE;
				}
			};
		}
	}

	@Override
	public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
		int lastChildPosition = -1;
		int childCount = mShowLastDivider ? parent.getChildCount() : parent.getChildCount() - 1;
		for (int i = 0; i < childCount; i++) {
			View child = parent.getChildAt(i);
			int childPosition = parent.getChildAdapterPosition(child);

			if (childPosition < lastChildPosition) {
				// Avoid remaining divider when animation starts
				continue;
			}
			lastChildPosition = childPosition;

			if (ViewCompat.getAlpha(child) < 1) {
				// Avoid remaining divider when animation starts
				continue;
			}

			if (mVisibilityProvider.shouldHideDivider(childPosition, parent)) {
				continue;
			}

			Rect bounds = getDividerBound(childPosition, parent, child);
			switch (mDividerType) {
				case DRAWABLE:
					Drawable drawable = mDrawableProvider.drawableProvider(childPosition, parent);
					drawable.setBounds(bounds);
					drawable.draw(c);
					break;
				case PAINT:
					mPaint = mPaintProvider.dividerPaint(childPosition, parent);
					c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
					break;
				case COLOR:
					mPaint.setColor(mColorProvider.dividerColor(childPosition, parent));
					mPaint.setStrokeWidth(mSizeProvider.dividerSize(childPosition, parent));
					c.drawLine(bounds.left, bounds.top, bounds.right, bounds.bottom, mPaint);
					break;
			}
		}
	}

	@Override
	public void getItemOffsets(Rect rect, View v, RecyclerView parent, RecyclerView.State state) {
		int position = parent.getChildAdapterPosition(v);
		setItemOffsets(rect, position, parent);
	}

	protected abstract Rect getDividerBound(int position, RecyclerView parent, View child);

	protected abstract void setItemOffsets(Rect outRect, int position, RecyclerView parent);

	/**
	 * Divider可视化控制接口
	 */
	public interface VisibilityProvider {
		/**
		 * 若Divider可隐藏,返回true
		 * @param position Divider position
		 * @param parent RecyclerView
		 * @return 若Divider可隐藏,返回true
		 */
		boolean shouldHideDivider(int position, RecyclerView parent);
	}

	/**
	 * Divider画笔控制接口
	 */
	public interface PaintProvider {
		/**
		 * 返回Divider画笔
		 * @param position Divider position
		 * @param parent RecyclerView
		 * @return 返回Divider画笔
		 */
		Paint dividerPaint(int position, RecyclerView parent);
	}

	/**
	 * Divider颜色控制接口
	 */
	public interface ColorProvider {
		/**
		 * 返回Divider颜色值
		 * @param position Divider position
		 * @param parent RecyclerView
		 * @return 返回Divider颜色值
		 */
		int dividerColor(int position, RecyclerView parent);
	}

	/**
	 * Divider Drawable控制接口
	 */
	public interface DrawableProvider {
		/**
		 * 返回Divider Drawable实例
		 * @param position Divider position
		 * @param parent RecyclerView
		 * @return 返回Divider Drawable实例
		 */
		Drawable drawableProvider(int position, RecyclerView parent);
	}

	/**
	 * Divider Drawable控制接口
	 */
	public interface SizeProvider {
		/**
		 * 返回Divider Size
		 * @param position Divider position
		 * @param parent RecyclerView
		 * @return 返回Divider Size
		 */
		int dividerSize(int position, RecyclerView parent);
	}

	public static class Builder<T extends Builder> {
		private Context mContext;
		Resources mResources;
		private PaintProvider mPaintProvider;
		private ColorProvider mColorProvider;
		private DrawableProvider mDrawableProvider;
		private SizeProvider mSizeProvider;
		private VisibilityProvider mVisibilityProvider = new VisibilityProvider() {
			@Override
			public boolean shouldHideDivider(int position, RecyclerView parent) {
				return false;
			}
		};

		private boolean mShowLastDivider = false;

		public Builder(Context context) {
			mContext = context;
			mResources = context.getResources();
		}

		public T paint(final Paint paint) {
			return paintProvider(new PaintProvider(){
				@Override
				public Paint dividerPaint(int position, RecyclerView parent) {
					return paint;
				}
			});
		}

		public T paintProvider(PaintProvider provider) {
			mPaintProvider = provider;
			return (T)this;
		}

		public T color(final int color) {
			return colorProvider(new ColorProvider() {
				@Override
				public int dividerColor(int position, RecyclerView parent) {
					return color;
				}
			});
		}

		public T colorResId(@ColorRes int colorId) {
			return color(mResources.getColor(colorId));
		}

		public T colorProvider(ColorProvider provider) {
			mColorProvider = provider;
			return (T)this;
		}

		public T drawable(@DrawableRes int id) {
			return drawable(mResources.getDrawable(id));
		}

		public T drawable(final Drawable drawable) {
			return drawableProvider(new DrawableProvider() {
				@Override
				public Drawable drawableProvider(int position, RecyclerView parent) {
					return drawable;
				}
			});
		}

		public T drawableProvider(DrawableProvider provider) {
			mDrawableProvider = provider;
			return (T) this;
		}

		public T size(final int size) {
			return sizeProvider(new SizeProvider() {
				@Override
				public int dividerSize(int position, RecyclerView parent) {
					return size;
				}
			});
		}

		public T sizeResId(@DimenRes int sizeId) {
			return size(mResources.getDimensionPixelSize(sizeId));
		}

		public T sizeProvider(SizeProvider provider) {
			mSizeProvider = provider;
			return (T) this;
		}

		public T visibilityProvider(VisibilityProvider provider) {
			mVisibilityProvider = provider;
			return (T) this;
		}

		public T showLastDivider() {
			mShowLastDivider = true;
			return (T) this;
		}

		protected void checkBuilderParams() {
			if (mPaintProvider != null) {
				if (mColorProvider != null) {
					throw new IllegalArgumentException(
							"Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.");
				}
				if (mSizeProvider != null) {
					throw new IllegalArgumentException(
							"Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.");
				}
			}
		}
	}
}
