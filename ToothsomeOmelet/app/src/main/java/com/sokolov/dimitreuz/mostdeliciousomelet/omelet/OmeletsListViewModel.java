package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.view.DishSearchEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OmeletsListViewModel extends BaseObservable
        implements OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO>,
        DishSearchEditText.OnSearchCompleteListener {

    @NonNull
    private final MutableLiveData<List<Omelet>> mOmelets;
    @NonNull
    private final OmeletRepository mRepository;

    public final ObservableInt placeholderVisibility = new ObservableInt(View.VISIBLE);

    public OmeletsListViewModel(@NonNull OmeletRepository repository) {
        this.mRepository = repository;
        this.mOmelets = new MutableLiveData<>();
        mOmelets.setValue(new ArrayList<>());
    }

    @Override
    public void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        if (!omelets.isEmpty()) {
            List<Omelet> list = Collections.unmodifiableList(omelets);
            placeholderVisibility.set(View.GONE);
            mOmelets.setValue(list);
        }
    }

    @Override
    public void onDataNotAvailable() {
        placeholderVisibility.set(View.GONE);
    }

    public void start() {
        mRepository.getOmelets(this);
    }

    public LiveData<List<Omelet>> getOmelets() {
        return mOmelets;
    }

    @Override
    public void onSearched(List<Omelet.OmeletDTO> omelets) {
        onOmeletsLoaded(omelets);
    }

    @Override
    public void onError() {
        placeholderVisibility.set(View.VISIBLE);
    }
}
