package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;

import java.util.List;

public class OmeletsListViewModel extends BaseObservable
        implements OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO> {

    @NonNull
    private final LiveData<List<Omelet>> mOmelets;
    @NonNull
    private final Context mContext;
    @NonNull
    private final OmeletRepository mRepository;

    public OmeletsListViewModel(@NonNull Context context, @NonNull OmeletRepository repository) {
        this.mContext = context;
        this.mRepository = repository;
        this.mOmelets = new MutableLiveData<>();
    }

    @Override
    public void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        List<Omelet> list = mOmelets.getValue();
        if (list != null) {
            mOmelets.getValue().addAll(omelets);
        }
    }

    @Override
    public void onDataNotAvailable() {

    }

    public void start() {
        mRepository.getOmelets(this);
    }

    public LiveData<List<Omelet>> getOmelets() {
        return mOmelets;
    }
}
