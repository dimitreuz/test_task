package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


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
    @Nullable
    private Executor mCurrentExecutor;

    public final ObservableBoolean placeholderVisibility = new ObservableBoolean(true);

    public final ObservableField<String> inputText = new ObservableField<>();

    public OmeletsListViewModel(@NonNull OmeletRepository repository) {
        this.mRepository = repository;
        this.mOmelets = new MutableLiveData<>();
        mOmelets.setValue(new ArrayList<>());
        registerInputTextObserver();
    }

    @Override
    public void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        if (!omelets.isEmpty()) {
            placeholderVisibility.set(false);
            List<Omelet> list = Collections.unmodifiableList(omelets);
            mOmelets.setValue(list);
        }
    }

    private void searchDish(@NonNull String dishName) {
        if (mCurrentExecutor != null) {
            if (mCurrentExecutor instanceof AppExecutors.CancelableFuturesExecutor) {
                AppExecutors.CancelableFuturesExecutor executor;
                executor = (AppExecutors.CancelableFuturesExecutor) mCurrentExecutor;
                executor.shutdownFutures();
            }
        }
        mCurrentExecutor = mRepository.searchForOmelets(OmeletsListViewModel.this, dishName);
    }

    private void registerInputTextObserver() {
        inputText.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                String text = inputText.get();
                if (TextUtils.isEmpty(text)) {
                    start();
                } else {
                    searchDish(text);
                }
            }
        });
    }

    @Override
    public void onDataNotAvailable() {
        placeholderVisibility.set(true);
    }

    public void start() {
        mRepository.getOmelets(this);
    }

    public LiveData<List<Omelet>> getOmelets() {
        return mOmelets;
    }

}
