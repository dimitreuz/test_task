package com.sokolov.dimitreuz.mostdeliciousomelet.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AbstractViewHolder<VB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private VB mBinding;

    public AbstractViewHolder(VB binding) {
        super(binding.getRoot());
    }

    public static<VB extends ViewDataBinding> AbstractViewHolder<VB> newInstance(
            @LayoutRes int layoutResId,
            @NonNull ViewGroup parent,
            boolean attachToRoot
    ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final VB dataBinding = DataBindingUtil.inflate(inflater, layoutResId, parent, attachToRoot);
        return new AbstractViewHolder<>(dataBinding);
    }

    public VB getBinding() {
        return mBinding;
    }
}
