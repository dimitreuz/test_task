package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;

import java.util.ArrayList;
import java.util.List;

public class OmeletsListViewModel extends BaseObservable
        implements OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO> {

    @NonNull
    private final LiveData<List<Omelet>> mOmelets;
    @NonNull
    private final OmeletRepository mRepository;

    public final ObservableInt placeholderVisibility = new ObservableInt(View.VISIBLE);

    public OmeletsListViewModel(@NonNull OmeletRepository repository) {
        this.mRepository = repository;
        this.mOmelets = new MutableLiveData<>();
        ((MutableLiveData<List<Omelet>>) mOmelets).setValue(new ArrayList<>());
    }

    @Override
    public void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        List<Omelet> list = mOmelets.getValue();
        if (list != null && list.isEmpty()) {
            list.clear();
            list.addAll(omelets);
            placeholderVisibility.set(View.GONE);
            ((MutableLiveData<List<Omelet>>) mOmelets).setValue(list);
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


}
