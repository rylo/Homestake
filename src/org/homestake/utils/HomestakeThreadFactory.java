package org.homestake.utils;

import java.util.concurrent.ThreadFactory;

public class HomestakeThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, "homestake-response-thread");
    }
}
