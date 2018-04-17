package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Current interface described data source for
 * omelet instance due to required context
 * (remote storage, local database and so on).
 *
 * It describes repository pattern for omelet instance.
 *
 * @param <T> type of Omelet required by context.
 * @see com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local.LocalOmeletDataSource
 * @see com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote.RemoteOmeletDataSource
 *
 * It provides methods to load instances from required
 * source in asynchronious and synchronious ways.
 *
 * To make asynchronious request you should use
 * @see ExecutionCallback
 * to retrieve data from the source.
 */
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
