package com.sokolov.dimitreuz.mostdeliciousomelet.ui.omelet;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sokolov.dimitreuz.mostdeliciousomelet.R;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletNavigator;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletsListViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.BaseFragment;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.OmeletsAdapter;
import com.sokolov.dimitreuz.mostdeliciousomelet.utils.Converters;

public class OmeletsListFragment extends BaseFragment<OmeletsListViewModel> implements OmeletNavigator, TextWatcher {

    @NonNull
    private EditText mEditText;
    @NonNull
    private OmeletsAdapter mAdapter;

    public OmeletsListFragment() {}

    public static OmeletsListFragment newInstance() {
        return new OmeletsListFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_omelets_list;
    }

    @Override
    public void initViews(@NonNull View view) {
        setupRecyclerView(view);
        setupSearchEditText(view);
        setupPlaceholder(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().start();
        getViewModel().getOmelets().observe(this, omelets -> {
            if (omelets != null) {
                mAdapter.setItems(Converters.convertToCachedOmelets(omelets));
            }
        });
        getViewModel().inputText.set(mEditText.getText().toString());
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.omelets_recyclerView);
        mAdapter = new OmeletsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    private void setupSearchEditText(View view) {
        mEditText = view.findViewById(R.id.dish_search_editText);
        mEditText.addTextChangedListener(this);
    }

    private void setupPlaceholder(View view) {
        TextView textView = view.findViewById(R.id.empty_list_view);
        ObservableBoolean visibilityField = getViewModel().placeholderVisibility;
        visibilityField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                int visibility = visibilityField.get() ? View.VISIBLE : View.GONE;
                textView.setVisibility(visibility);
            }
        });
    }

    @Override
    public void onOmeletItemClick(@Nullable String href) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
        startActivity(browserIntent);
    }

    @Override
    public void onStop() {
        getViewModel().getOmelets().removeObservers(this);
        getViewModel().stop();
        super.onStop();
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
        getViewModel().inputText.set(s.toString());
    }
}
