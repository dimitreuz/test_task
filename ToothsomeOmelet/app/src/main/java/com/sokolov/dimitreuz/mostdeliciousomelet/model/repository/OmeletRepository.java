package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.local.LocalOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote.RemoteOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.utils.Converters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class OmeletRepository extends AbstractOmeletDataSource<Omelet.OmeletDTO>{

    @NonNull
    private OmeletDataSource<OmeletDB> mLocalDataSource;
    @NonNull
    private OmeletDataSource<OmeletAPI> mRemoteDataSource;
    @NonNull
    private List<Omelet.OmeletDTO> mCachedOmelets;

    private OmeletRepository(AppExecutors appExecutors, Context context) {
        super(appExecutors);
        mLocalDataSource = new LocalOmeletDataSource(appExecutors, context);
        mRemoteDataSource = new RemoteOmeletDataSource(appExecutors);
        mCachedOmelets = new ArrayList<>();
    }

    private static OmeletRepository mInstance;

    public static OmeletRepository getInstance(AppExecutors appExecutors, Context context) {
        if (mInstance == null) {
            mInstance = new OmeletRepository(appExecutors, context);
        }
        return mInstance;
    }

    @Override
    public void getOmelets(@NonNull ExecutionCallback<Omelet.OmeletDTO> callback) {
        if (mCachedOmelets.isEmpty()) {
            mLocalDataSource.getOmelets(new ExecutionCallback<OmeletDB>() {

                @Override
                public void onOmeletsLoaded(List<OmeletDB> omelets) {
                    mCachedOmelets = Converters.convertToCachedOmelets(omelets);
                    executeOnMainThread(() -> callback.onOmeletsLoaded(mCachedOmelets));
                }

                @Override
                public void onDataNotAvailable() {
                    preCacheRemoteOmelets(callback);
                }
            });
        } else {
            callback.onOmeletsLoaded(mCachedOmelets);
        }
    }

    private void preCacheRemoteOmelets(@NonNull ExecutionCallback<Omelet.OmeletDTO> callback) {
        mRemoteDataSource.getOmelets(new ExecutionCallback<OmeletAPI>() {
            @Override
            public void onOmeletsLoaded(List<OmeletAPI> omelets) {
                mCachedOmelets = saveOmeletsLocally(omelets);
                executeOnMainThread(() -> callback.onOmeletsLoaded(mCachedOmelets));
            }

            @Override
            public void onDataNotAvailable() { }
        });
    }

    private List<Omelet.OmeletDTO> saveOmeletsLocally(List<OmeletAPI> omelets) {
        LocalOmeletDataSource source = (LocalOmeletDataSource) mLocalDataSource;
        Executor diskExecutor = getAppExecutors().getExecutor(AppExecutors.DISK);
        diskExecutor.execute(() -> {
            source.getOmeletDAO().insertAll(Converters.convertToLocalOmelets(omelets));
        });
        return Converters.convertToCachedOmelets(omelets);
    }

    public void executeOnMainThread(Runnable runnable) {
        Executor uiExecutor = getAppExecutors().getExecutor(AppExecutors.UI);
        uiExecutor.execute(runnable);
    }
}
