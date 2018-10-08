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
 * 生命周期感知的可观察性，在订阅后仅发送新的更新，用于导航和Snackbar消息等事件。<br>
 * 这避免了事件的常见问题：在配置更改（如轮换）时，如果观察者处于活动状态，则可以发出更新。<br>
 * 如果存在对setValue（）或call（）的显式调用，则此LiveData仅调用observable。<br>
 * <b>请注意，只有一名观察员将被通知更改。</b>
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
        if(hasActiveObservers()) {
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

    // 发生数据变更时更改触发事件开关为false并发送事件
    private void observerChange(Observer<T> observer, T t) {
        if(pending.compareAndSet(true, false)) {
            observer.onChanged(t);
        }
    }
}
