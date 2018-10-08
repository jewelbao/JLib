package com.jewel.lib.android.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 数据源事件
 * @param <T>
 */
@SuppressWarnings("unused")
public class DataSourceEvent<T> extends SingleLiveEvent<T> {
    private static final int EVENT_NORMAL = 0;
    private static final int EVENT_SUCCESS = 1;
    private static final int EVENT_EMPTY = 2;
    private static final int EVENT_ERROR = 3;

    private int event;
    private Throwable throwable;

    public DataSourceEvent() {
        this.event = EVENT_NORMAL;
    }

    /**
     * 将数据源定义为正常数据事件并发送通知给订阅者
     */
    public void setData(@NonNull T data) {
        this.event = EVENT_SUCCESS;
        super.setValue(data);
    }

    /**
     * 将数据源定义为空数据事件并发送通知给订阅者
     */
    public void setEmpty(){
        this.event = EVENT_EMPTY;
        super.setValueForVoid();
    }

    /**
     * 将数据源定义为异常数据事件并发送通知给订阅者
     */
    public void setError(Throwable throwable) {
        this.event = EVENT_ERROR;
        this.throwable = throwable;
        super.setValueForVoid();
    }

    /**
     * 订阅获取数据源事件
     * @param owner 上下文
     * @param observer 数据源观察者
     */
    public void observe(LifecycleOwner owner, final DataSourceObserver<T> observer) {
       super.observe(owner, new Observer<T>() {
           @Override
           public void onChanged(@Nullable T t) {
               switch (event) {
                   case EVENT_SUCCESS:
                       observer.onGetDataSuccess(t);
                       break;
                   case EVENT_EMPTY:
                       observer.onGetEmptyData();
                       break;
                   case EVENT_ERROR:
                       observer.onGetDataError(throwable);
                       break;
               }
           }
       });
    }

    /**
     * 数据源观察者接口
     * @param <T>
     */
    public interface DataSourceObserver<T> {
        /**
         * 获取到正常业务数据。当{@link DataSourceEvent#setData(Object)}或者{@link DataSourceEvent#setValue(Object)}不为null被调用时，此事件触发。
         * @param data {@link DataSourceEvent#setData(Object)}设置进去的数据
         */
        void onGetDataSuccess(T data);

        /**
         * 获取到空数据。当{@link DataSourceEvent#setEmpty()}或者{@link DataSourceEvent#setValue(Object)}为null被调用时，此事件触发。
         */
        void onGetEmptyData();

        /**
         * 获取数据发生异常。当{@link DataSourceEvent#setError(Throwable)}被调用时，此事件触发。
         * @param throwable {@link DataSourceEvent#setError(Throwable)}设置进去的数据
         */
        void onGetDataError(Throwable throwable);
    }
}
