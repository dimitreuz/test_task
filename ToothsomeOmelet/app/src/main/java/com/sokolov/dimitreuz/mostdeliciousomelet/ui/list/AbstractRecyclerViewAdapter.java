package com.sokolov.dimitreuz.mostdeliciousomelet.ui.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRecyclerViewAdapter<Model, VB extends ViewDataBinding>
        extends RecyclerView.Adapter<AbstractViewHolder<VB>> {

    private List<Model> models;

    protected AbstractRecyclerViewAdapter() {
        models = new ArrayList<>();
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
        VB binding = holder.getBinding();
        bind(binding, models.get(position));
        binding.executePendingBindings();
    }

    @Override
    public void onViewRecycled(@NonNull AbstractViewHolder<VB> holder) {
        VB binding = holder.getBinding();
        binding.unbind();
        unbind(binding);
        super.onViewRecycled(holder);
    }

    public abstract void bind(VB binding, Model model);

    public abstract void unbind(VB binding);

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
