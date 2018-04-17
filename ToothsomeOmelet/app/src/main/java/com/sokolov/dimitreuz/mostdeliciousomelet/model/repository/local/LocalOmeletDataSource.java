package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.AppDatabase;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.OmeletDAO;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class LocalOmeletDataSource extends AbstractOmeletDataSource<OmeletDB> {

    private AppDatabase mAppDatabase;

    public LocalOmeletDataSource(@NonNull AppExecutors appExecutors, @NonNull Context context) {
        super(appExecutors);
        mAppDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public void getOmeletsAsync(ExecutionCallback<OmeletDB> callback) {
        getAppExecutors().getExecutor(AppExecutors.DISK).execute(
                () -> {
                    List<OmeletDB> omelets = getOmelets();
                    if (omelets.isEmpty()) {
                        callback.onDataNotAvailable();
                    } else {
                        callback.onOmeletsLoaded(omelets);
                    }
                }
        );

    }

    @Override
    public List<OmeletDB> getOmelets() {
        try {
            return mAppDatabase.getOmeletDAO().getAll();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void searchForDishesAsync(@NonNull ExecutionCallback<OmeletDB> callback, @NonNull String dishName) {
        Executor executor = getAppExecutors().getExecutor(AppExecutors.DISK);
        executor.execute(() -> {
            List<OmeletDB> omelets = searchForDishes(dishName);
            if (omelets.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onOmeletsLoaded(omelets);
            }
        });
    }

    @Override
    public List<OmeletDB> searchForDishes(@NonNull String dishName) {
        try {
            return mAppDatabase.getOmeletDAO().findRequiredOmelets(dishName);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public OmeletDAO getOmeletDAO() {
        return mAppDatabase.getOmeletDAO();
    }
}
