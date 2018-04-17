package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.AppDatabase;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.OmeletDAO;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;

import java.util.List;
import java.util.concurrent.Executor;

public class LocalOmeletDataSource extends AbstractOmeletDataSource<OmeletDB> {

    private AppDatabase mAppDatabase;

    public LocalOmeletDataSource(@NonNull AppExecutors appExecutors, @NonNull Context context) {
        super(appExecutors);
        mAppDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public void getOmelets(ExecutionCallback<OmeletDB> callback) {
        getAppExecutors().getExecutor(AppExecutors.DISK).execute(
                () -> {
                    try {
                        List<OmeletDB> omelets = mAppDatabase.getOmeletDAO().getAll();
                        if (omelets == null || omelets.isEmpty()) {
                            callback.onDataNotAvailable();
                        } else {
                            callback.onOmeletsLoaded(omelets);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onDataNotAvailable();
                    }
                }
        );

    }

    @Override
    public Executor searchForOmelets(@NonNull ExecutionCallback<OmeletDB> callback, @NonNull String dishName) {
        Executor executor = getAppExecutors().getExecutor(AppExecutors.DISK);
        executor.execute(() -> {
            List<OmeletDB> omelets = mAppDatabase.getOmeletDAO().findRquiredoOmelets(dishName);
            try {
                if (omelets == null) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onOmeletsLoaded(omelets);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onDataNotAvailable();
            }
        });
        return executor;
    }

    public OmeletDAO getOmeletDAO() {
        return mAppDatabase.getOmeletDAO();
    }
}
