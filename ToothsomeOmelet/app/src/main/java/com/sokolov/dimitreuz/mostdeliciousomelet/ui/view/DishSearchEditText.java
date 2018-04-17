package com.sokolov.dimitreuz.mostdeliciousomelet.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class DishSearchEditText extends AppCompatEditText
        implements TextWatcher, OmeletDataSource.ExecutionCallback<Omelet.OmeletDTO> {

    @NonNull
    private final OmeletRepository mRepository;
    @Nullable
    private Executor mCurrentExecutor;
    @NonNull
    private final List<OnSearchCompleteListener> mListeners;

    public DishSearchEditText(Context context) {
        this(context, null);
    }

    public DishSearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DishSearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mListeners = new ArrayList<>();
        mRepository = OmeletRepository.getInstance(new AppExecutors(), getContext());
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        /* IGNORED */
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /* IGNORED */
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mCurrentExecutor != null) {
            if (mCurrentExecutor instanceof AppExecutors.CancelableFuturesExecutor) {
                AppExecutors.CancelableFuturesExecutor executor;
                executor = (AppExecutors.CancelableFuturesExecutor) mCurrentExecutor;
                executor.shutdownFutures();
            }
        }
        mCurrentExecutor = mRepository.searchForOmelets(this, s.toString());
    }

    public void addSearchListener(@NonNull OnSearchCompleteListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void onOmeletsLoaded(List<Omelet.OmeletDTO> omelets) {
        for (OnSearchCompleteListener listener : mListeners) {
            listener.onSearched(omelets);
        }
    }

    @Override
    public void onDataNotAvailable() {
        for (OnSearchCompleteListener listener : mListeners) {
            listener.onError();
        }
    }

    public interface OnSearchCompleteListener {

        void onSearched(List<Omelet.OmeletDTO> omelets);

        void onError();
    }
}
