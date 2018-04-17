package com.sokolov.dimitreuz.mostdeliciousomelet.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;

import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.util.List;

public class DishSearchEditText extends android.support.v7.widget.AppCompatEditText implements TextWatcher {

    @NonNull
    private final AppExecutors mAppExecutors;
    @NonNull
    private Thread mTextProcessingThread;
    @NonNull
    private PipedReader mReader;
    @NonNull
    private PipedWriter mWriter;


    public DishSearchEditText(Context context) {
        this(context, null);
    }

    public DishSearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DishSearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTextChangedListener(this);
        mAppExecutors = new AppExecutors();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void onResult(List<Omelet.OmeletDTO> omelets) {

    }

    public void onActivityDestroyed() {
        removeTextChangedListener(this);
    }
}
