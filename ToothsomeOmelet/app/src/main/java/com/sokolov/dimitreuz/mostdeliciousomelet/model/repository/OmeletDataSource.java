package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;

import java.util.List;
import java.util.concurrent.Executor;

public interface OmeletDataSource<T extends Omelet> {

    interface ExecutionCallback<T extends Omelet> {

        void onOmeletsLoaded(List<T> omelets);

        void onDataNotAvailable();
    }

    void getOmeletsAsync(@NonNull ExecutionCallback<T> callback);

    List<T> getOmelets();

    void searchForDishesAsync(@NonNull ExecutionCallback<T> callback, @NonNull String dishName);

    List<T> searchForDishes(@NonNull String dishName);
}
