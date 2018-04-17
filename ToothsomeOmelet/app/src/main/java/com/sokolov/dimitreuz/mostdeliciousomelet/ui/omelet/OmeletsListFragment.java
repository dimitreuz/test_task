package com.sokolov.dimitreuz.mostdeliciousomelet.ui.omelet;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sokolov.dimitreuz.mostdeliciousomelet.R;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletNavigator;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletsListViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.BaseFragment;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.OmeletsAdapter;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.view.DishSearchEditText;
import com.sokolov.dimitreuz.mostdeliciousomelet.utils.Converters;

public class OmeletsListFragment extends BaseFragment<OmeletsListViewModel> implements OmeletNavigator {

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
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().getOmelets().observe(this, omelets -> {
            if (omelets != null) {
                mAdapter.setItems(Converters.convertToCachedOmelets(omelets));
            }
        });
        getViewModel().start();
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.omelets_recyclerView);
        mAdapter = new OmeletsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    private void setupSearchEditText(View view) {
        DishSearchEditText editText = view.findViewById(R.id.dish_search_editText);
        editText.addSearchListener(getViewModel());
    }

    @Override
    public void onOmeletItemClick(@Nullable String href) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
        startActivity(browserIntent);
    }

    @Override
    public void onPause() {
        getViewModel().getOmelets().removeObservers(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
