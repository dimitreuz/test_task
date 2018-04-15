package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local.LocalOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote.RemoteOmeletDataSource;

import java.util.ArrayList;
import java.util.List;

public class OmeletRepository implements OmeletDataSource<Omelet.OmeletDTO>{

    @NonNull
    private OmeletDataSource<OmeletDB> mLocalDataSource;
    @NonNull
    private OmeletDataSource<OmeletAPI> mRemoteDataSource;
    @NonNull
    private List<Omelet.OmeletDTO> mCachedOmelets;

    private OmeletRepository(Context context) {
        mLocalDataSource = new LocalOmeletDataSource(context);
        mRemoteDataSource = new RemoteOmeletDataSource();
        mCachedOmelets = new ArrayList<>();
    }

    private static OmeletRepository mInstance;

    public static OmeletRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OmeletRepository(context);
        }
        return mInstance;
    }

    @Override
    public List<Omelet.OmeletDTO> getOmelets(ExecutionCallback callback) {
        return null;
    }
}
