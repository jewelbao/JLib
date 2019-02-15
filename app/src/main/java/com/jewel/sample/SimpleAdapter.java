package com.jewel.sample;

import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleAdapter<T> extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private List<T> data = new ArrayList<>();
    private int adapterLayoutRes;
    private OnItemClickListener<T> itemClickListener;

    public abstract void bindView(SimpleViewHolder viewHolder, T data, int position);

    public SimpleAdapter(@LayoutRes int layoutRes) {
        this(null, layoutRes);
    }

    public SimpleAdapter(@Nullable List<T> data, @LayoutRes int layoutRes) {
        this.data = data;
        this.adapterLayoutRes = layoutRes;
    }

    ////////////////////////////////////公共方法区域////////////////////////////////////////////

    public void setData(List<T> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public void addData(List<T> newDataList) {
        if (newDataList == null || newDataList.isEmpty()) {
            return;
        }
        if (data == null) {
            data = new ArrayList<>();
        }

        data.addAll(newDataList);
        notifyItemRangeInserted(getData().size(), data.size());
    }

    public void addData(T newData) {
        if (newData == null) {
            return;
        }
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(newData);
        notifyItemInserted(getItemCount());
    }

    public List<T> getData() {
        if(data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View adapterView = View.inflate(viewGroup.getContext(), adapterLayoutRes, null);
        final SimpleViewHolder viewHolder = new SimpleViewHolder(adapterView) {
        };
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemViewClickListener(viewHolder);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder viewHolder, int position) {
        bindView(viewHolder, getData().get(position), position);
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {

        private SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public final <T extends View> T findViewById(int id) {
            return itemView.findViewById(id);
        }

        public final SimpleViewHolder setText(int id, int stringRes) {
            View view = findViewById(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(itemView.getContext().getResources().getText(stringRes));
            }
            return this;
        }

        public final SimpleViewHolder setText(int id, String text) {
            View view = findViewById(id);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        public final SimpleViewHolder setImageResource(int id, int drawableRes) {
            if(drawableRes == -1) {
                return this;
            }
            View view = findViewById(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            }
            return this;
        }

        public final SimpleViewHolder setImageDrawable(int id, Drawable drawableRes) {
            if(drawableRes == null) {
                return this;
            }
            View view = findViewById(id);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(drawableRes);
            }
            return this;
        }
    }

    /**
     * item clicked listener
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        void onItemClicked(View view, T data, int position);
    }

    ////////////////////////////////////私有方法区域////////////////////////////////////////////

    private void onItemViewClickListener(RecyclerView.ViewHolder viewHolder) {
        if (itemClickListener != null) {
            int clickedPos = viewHolder.getLayoutPosition() % getData().size();
            itemClickListener.onItemClicked(viewHolder.itemView, getData().get(clickedPos), clickedPos);
        }
    }
}
