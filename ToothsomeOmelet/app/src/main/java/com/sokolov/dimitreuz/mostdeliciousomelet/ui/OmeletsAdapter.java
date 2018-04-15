package com.sokolov.dimitreuz.mostdeliciousomelet.ui;

import android.databinding.ViewDataBinding;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.omelet.OmeletItemViewModel;
import com.sokolov.dimitreuz.mostdeliciousomelet.ui.list.AbstractRecyclerViewAdapter;

public class OmeletsAdapter extends AbstractRecyclerViewAdapter<Omelet.OmeletDTO, ViewDataBinding> {

    public OmeletsAdapter(int modelId) {
        super(modelId);
    }

    @Override
    public int getLayoutResId() {
        return 0;
    }
}
