package com.sokolov.dimitreuz.mostdeliciousomelet.model;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    public static final int NET = 0;
    public static final int DISK = 1;
    public static final int UI = 3;

    @IntDef({NET, DISK, UI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExecutingContext {}

    @NonNull
    private final Executor mNetExecutor;
    @NonNull
    private final Executor mDiskIOExecutor;
    @NonNull
    private final Executor mMainThreadExecutor;

    AppExecutors(Executor netExecutor, Executor diskExecutor, Executor uiExecutor) {
        this.mNetExecutor = netExecutor;
        this.mDiskIOExecutor = diskExecutor;
        this.mMainThreadExecutor = uiExecutor;
    }

    public AppExecutors() {
        this(new NetExecutor(), new DiskIOThreadExecutor(), new MainThreadExecutor());
    }

    private static final String EXCEPTION_MESSAGE = "CONTEXT_NOT_SUPPORTED";

    public Executor getExecutor(@ExecutingContext int context) {
        switch (context) {
            case NET: return mNetExecutor;
            case DISK: return mDiskIOExecutor;
            case UI: return mMainThreadExecutor;
            default: throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        }
    }

    private static class NetExecutor implements Executor {

        private static final int THREAD_COUNT = 2;

        private final Executor mNetExecutor;

        public NetExecutor() {
            mNetExecutor = Executors.newFixedThreadPool(THREAD_COUNT);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mNetExecutor.execute(command);
        }
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    private static final class DiskIOThreadExecutor implements Executor {

        private final Executor mDiskExecutor;

        public DiskIOThreadExecutor() {
            mDiskExecutor = Executors.newSingleThreadExecutor();
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mDiskExecutor.execute(command);
        }
    }
}
