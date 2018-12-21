package com.jewel.lib.android.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
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
     * Define the data source as a normal data event and send a notification to the subscriber
     */
    public void setData(@NonNull T data) {
        this.event = EVENT_SUCCESS;
        super.setValue(data);
    }

    /**
     * Define a data source as an empty data event and send a notification to the subscriber
     */
    public void setEmpty(){
        this.event = EVENT_EMPTY;
        super.setValueForVoid();
    }

    /**
     * Define the data source as an exception data event and send a notification to the subscribe
     */
    public void setError(Throwable throwable) {
        this.event = EVENT_ERROR;
        this.throwable = throwable;
        super.setValueForVoid();
    }

    /**
     * Subscribe to get data source events
     * @param owner Context
     * @param observer Data source observer
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
     * Data source observer interface
     */
    public interface DataSourceObserver<T> {
        /**
         * Get normal business data.This event fires when {@link DataSourceEvent#setData(Object)} or {@link DataSourceEvent#setValue(Object)} (not null) is called.
         * @param data {@link DataSourceEvent#setData(Object)}Set the data in
         */
        void onGetDataSuccess(T data);

        /**
         * Get empty data. This event fires when {@link DataSourceEvent#setEmpty()} or {@link DataSourceEvent#setValue(Object)} (which is null) is called.
         */
        void onGetEmptyData();

        /**
         * An exception occurred in getting the data. This event fires when {@link DataSourceEvent#setError(Throwable)} is called.
         * @param throwable {@link DataSourceEvent#setError(Throwable)}Set the data in
         */
        void onGetDataError(Throwable throwable);
    }
}
