package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.AppDatabase;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.OmeletDAO;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;

import java.util.List;

public class LocalOmeletDataSource extends AbstractOmeletDataSource<OmeletDB> {

    private AppDatabase mAppDatabse;

    public LocalOmeletDataSource(@NonNull AppExecutors appExecutors, @NonNull Context context) {
        super(appExecutors);
        mAppDatabse = AppDatabase.getInstance(context);
    }

    @Override
    public void getOmelets(ExecutionCallback<OmeletDB> callback) {
        try {
            List<OmeletDB> omelets = mAppDatabse.getOmeletDAO().getAll();
            if (omelets == null || omelets.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onOmeletsLoaded(omelets);
            }
        } catch (Exception e) {
            callback.onDataNotAvailable();
        }
    }

    public OmeletDAO getOmeletDAO() {
        return mAppDatabse.getOmeletDAO();
    }
}
