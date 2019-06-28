/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jewel.lib.android;

import android.os.Handler;
import android.os.Looper;

/**
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
public class HandlerUtil {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    /**
     * Causes the Runnable to be added to the message queue. The runnable will be run on the thread to which this handler is attached
     *
     * @return Returns true if the Runnable was successfully placed in to the message queue. Returns false on failure, usually because the looper processing the message queue is exiting
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return HANDLER.post(runnable);
    }

    /**
     * Causes the Runnable r to be added to the message queue, to be run after the specified amount of time elapses. The runnable will be run on the thread to which this handler is attached.
     *
     * @return Returns true if the Runnable was successfully placed in to the message queue. Returns false on failure, usually because the looper processing the message queue is exiting. Note that a result of true does not mean the Runnable will be processed -- if the looper is quit before the delivery time of the message occurs then the message will be dropped.
     */
    public static boolean runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        return HANDLER.postDelayed(runnable, delayMillis);
    }

    /**
     * Remove any pending posts of Runnable r that are in the message queue.
     */
    public static void removeRunnable(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

    /**
     * Remove any pending posts of messages with code 'what' that are in the message queue.
     */
    public static void removeMessages(int what) {
        HANDLER.removeMessages(what);
    }

    /**
     * Remove all callbacks and messages
     */
    public static void removeAll() {
        HANDLER.removeCallbacksAndMessages(null);
    }

}
