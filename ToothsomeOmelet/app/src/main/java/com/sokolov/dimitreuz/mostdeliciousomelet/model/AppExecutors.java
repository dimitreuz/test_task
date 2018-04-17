package com.sokolov.dimitreuz.mostdeliciousomelet.model;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private static class NetExecutor extends CancelableFuturesExecutor {

        private static final int THREAD_COUNT = 2;

        NetExecutor() {
            super(Executors.newFixedThreadPool(THREAD_COUNT));
        }
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler;

        public MainThreadExecutor() {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }

        public Handler getMainThreadHandler() {
            return mainThreadHandler;
        }
    }

    private static final class DiskIOThreadExecutor extends CancelableFuturesExecutor {
        DiskIOThreadExecutor() {
            super(Executors.newSingleThreadExecutor());
        }
    }

    public static abstract class CancelableFuturesExecutor implements Executor {

        private final ExecutorService mService;

        private volatile Future<?> mLastFuture;

        public CancelableFuturesExecutor(ExecutorService executorService) {
            this.mService = executorService;
        }

        @Override
        public void execute(@NonNull Runnable command) {
            mLastFuture = mService.submit(command);
        }

        public boolean cancelLastTask() {
            return mLastFuture != null && !mLastFuture.isDone() && mLastFuture.cancel(true);
        }
    }
}
