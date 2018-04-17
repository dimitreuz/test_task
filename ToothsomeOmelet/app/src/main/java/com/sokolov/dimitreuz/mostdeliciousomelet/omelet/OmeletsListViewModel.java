package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.view.DishSearchEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OmeletsListViewModel extends BaseObservable
        implements OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO> {

    @NonNull
    private final MutableLiveData<List<Omelet>> mOmelets;
    @NonNull
    private final OmeletRepository mRepository;

    public final ObservableInt placeholderVisibility = new ObservableInt(View.VISIBLE);

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
            List<Omelet> list = Collections.unmodifiableList(omelets);
            placeholderVisibility.set(View.GONE);
            mOmelets.setValue(list);
        }
    }

    private void registerInputTextObserver() {
        inputText.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                String text = inputText.get();
                if (TextUtils.isEmpty(text)) {
                    start();
                } else {
                    mRepository.searchForOmelets(OmeletsListViewModel.this, text);
                }
            }
        });
    }

    @Override
    public void onDataNotAvailable() {
        placeholderVisibility.set(View.VISIBLE);
    }

    public void start() {
        mRepository.getOmelets(this);
    }

    public LiveData<List<Omelet>> getOmelets() {
        return mOmelets;
    }

}
