package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class OmeletsListViewModel extends BaseObservable
        implements OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO> {

    @NonNull
    private final MutableLiveData<List<Omelet>> mOmelets;
    @NonNull
    private final OmeletRepository mRepository;

    public final ObservableBoolean placeholderVisibility = new ObservableBoolean(true);

    public final ObservableField<String> inputText = new ObservableField<>();

    @Nullable
    private HandlerThread mHandlerThread;
    @Nullable
    private Handler mHandler;

    public OmeletsListViewModel(@NonNull OmeletRepository repository) {
        this.mRepository = repository;
        this.mOmelets = new MutableLiveData<>();
        mOmelets.setValue(new ArrayList<>());
    }

    @Override
    public synchronized void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        placeholderVisibility.set(!omelets.isEmpty());
        List<Omelet> list = Collections.unmodifiableList(omelets);
        mOmelets.setValue(list);
    }

    @Override
    public void onDataNotAvailable() {
        placeholderVisibility.set(true);
    }

    private OnPropertyChangedCallback mCallback = new OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            String text = inputText.get();
            mHandler.post(() -> {
                List<Omelet.OmeletDTO> omelets = mRepository.searchForDishes(text);
                Executor executor = mRepository.getAppExecutors().getExecutor(AppExecutors.UI);
                executor.execute(() -> {
                    placeholderVisibility.set(omelets.isEmpty());
                    mOmelets.setValue(Collections.unmodifiableList(omelets));
                });
            });
        }
    };

    public LiveData<List<Omelet>> getOmelets() {
        return mOmelets;
    }

    private static final String HANDLER_THREAD_NAME = "HANDLER_THREAD_NAME";

    private void startHandlerThread() {
        mHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    public void start() {
        mRepository.getOmeletsAsync(this);
        startHandlerThread();
        inputText.addOnPropertyChangedCallback(mCallback);
    }

    public void stop() {
        inputText.removeOnPropertyChangedCallback(mCallback);
        if (mHandlerThread != null) {
            mHandlerThread.quit();
        }
        mHandlerThread = null;
        mHandler = null;
    }

}
