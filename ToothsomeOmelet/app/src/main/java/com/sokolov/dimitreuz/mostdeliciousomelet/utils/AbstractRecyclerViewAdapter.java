package com.sokolov.dimitreuz.mostdeliciousomelet.utils;

import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractRecyclerViewAdapter<Model, VB extends ViewDataBinding>
        extends RecyclerView.Adapter<AbstractViewHolder<VB>> {

    private List<Model> models;

    @IdRes
    private int mModelId;

    public AbstractRecyclerViewAdapter(@IdRes int modelId) {
        this.mModelId = modelId;
    }

    public void setItems(Model... items) {
        setItems(Arrays.asList(items));
    }

    public void setItems(List<Model> items) {
        models = items;
        notifyDataSetChanged();
    }

    public void addItem(Model item) {
        models.add(item);
        notifyItemInserted(models.size() - 1);
    }

    public void removeItem(Model item) {
        int index = models.indexOf(item);
        models.remove(index);
        notifyItemRemoved(index);
    }

    @NonNull
    @Override
    public AbstractViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AbstractViewHolder.newInstance(getLayoutResId(), parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder<VB> holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(mModelId, models.get(position));
        binding.executePendingBindings();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AbstractViewHolder<VB> holder) {
        holder.getBinding().unbind();
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @LayoutRes
    public abstract int getLayoutResId();
}
