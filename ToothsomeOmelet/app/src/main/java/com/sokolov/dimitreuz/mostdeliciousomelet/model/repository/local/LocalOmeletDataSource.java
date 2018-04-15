package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local;

import android.content.Context;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.AppDatabase;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.database.OmeletDAO;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;

import java.util.List;

public class LocalOmeletDataSource implements OmeletDataSource<OmeletDB> {

    private AppDatabase mAppDatabse;

    public LocalOmeletDataSource(Context context) {
        mAppDatabse = AppDatabase.getInstance(context);
    }

    @Override
    public List<OmeletDB> getOmelets(ExecutionCallback callback) {
        return mAppDatabse.getOmeletDAO().getAll();
    }

    public OmeletDAO getOmeleteDAO() {
        return mAppDatabse.getOmeletDAO();
    }
}
