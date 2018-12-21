package com.jewel.lib.android.live;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Observability of lifecycle awareness, only new updates are sent after subscription, for events such as navigation and Snackbar messages. <br>
 *   This avoids the common problem of events: when a configuration change (such as a rotation), an observer can be issued an update if it is active. <br>
 *   If there is an explicit call to setValue() or call(), then this LiveData only calls the observable. <br>
 *   <b>Please note that only one observer will be notified of the change. </b>
 *
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
@SuppressWarnings("unused")
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private static final String TAG = "LiveEvent";

    private final AtomicBoolean pending = new AtomicBoolean(false); // 是否触发事件

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<T> observer) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.");
        }
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                observerChange(observer, t);
            }
        });
    }

    @Override
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }

    @MainThread
    public void setValueForVoid() {
        setValue(null);
    }

    // Change the trigger event switch to false and send an event when a data change occurs
    private void observerChange(Observer<T> observer, T t) {
        if (pending.compareAndSet(true, false)) {
            observer.onChanged(t);
        }
    }
}
