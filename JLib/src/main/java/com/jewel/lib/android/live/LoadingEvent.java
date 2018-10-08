package com.jewel.lib.android.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 加载事件
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
@SuppressWarnings("unused")
public class LoadingEvent extends SingleLiveEvent<Integer> {

    public static final int EVENT_NORMAL = 0;
    public static final int EVENT_LOADING = 1;
    public static final int EVENT_LOADED_FINISH = 2;
    public static final int EVENT_LOAD_CANCEL = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EVENT_NORMAL, EVENT_LOAD_CANCEL, EVENT_LOADED_FINISH, EVENT_LOADING})
    public @interface EventValue{}

    /**
     * @param value {@link #EVENT_NORMAL}、{@link #EVENT_LOADING}、{@link #EVENT_LOADED_FINISH}、{@link #EVENT_LOAD_CANCEL}
     */
    @Override
    public void setValue(@EventValue Integer value) {
        if(value == null) {
            value = EVENT_NORMAL;
        }
        super.setValue(value);
    }

    /**
     * 订阅加载事件
     * @param owner 上下文
     * @param observer 加载事件观察者
     */
    public void observe(LifecycleOwner owner, final LoadingObserver observer) {
        super.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer event) {
                if(event != null) {
                    switch (event) {
                        case EVENT_LOADING:
                            observer.onLoading();
                            break;
                        case EVENT_LOADED_FINISH:
                            observer.onLoadedComplete();
                            break;
                        case EVENT_LOAD_CANCEL:
                            observer.onLoadCancel();
                            break;
                        case EVENT_NORMAL:
                            observer.onLoadReset();
                            break;
                    }
                }
            }
        });
    }

    /**
     * 加载事件观察者
     */
    public interface LoadingObserver {
        /**
         * 重置加载事件，当{@link LoadingEvent#setValue(Integer)}值为{@link #EVENT_NORMAL}时触发。
         */
        void onLoadReset();
        /**
         * 加载中事件，当{@link LoadingEvent#setValue(Integer)}值为{@link #EVENT_LOADING}时触发。
         */
        void onLoading();
        /**
         * 取消加载事件，当{@link LoadingEvent#setValue(Integer)}值为{@link #EVENT_LOAD_CANCEL}时触发。
         */
        void onLoadCancel();
        /**
         * 加载完成事件，当{@link LoadingEvent#setValue(Integer)}值为{@link #EVENT_LOADED_FINISH}时触发。
         */
        void onLoadedComplete();
    }

    public static class DefaultLoadingObserver implements LoadingObserver {

        @Override
        public void onLoadReset() {

        }

        @Override
        public void onLoading() {

        }

        @Override
        public void onLoadCancel() {

        }

        @Override
        public void onLoadedComplete() {

        }
    }
}
