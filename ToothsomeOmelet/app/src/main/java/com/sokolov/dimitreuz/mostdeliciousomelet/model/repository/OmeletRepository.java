package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

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

public class OmeletRepository extends AbstractOmeletDataSource<Omelet.OmeletDTO> {

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
    public void getOmeletsAsync(@NonNull ExecutionCallback<Omelet.OmeletDTO> callback) {
        if (mCachedOmelets.isEmpty()) {
            mLocalDataSource.getOmeletsAsync(new ExecutionCallback<OmeletDB>() {

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

    @Override
    public List<Omelet.OmeletDTO> getOmelets() {
        if (mCachedOmelets.isEmpty()) {
            mCachedOmelets = Converters.convertToCachedOmelets(mRemoteDataSource.getOmelets());
            if (mCachedOmelets.isEmpty()) {
                mCachedOmelets = Converters.convertToCachedOmelets(mLocalDataSource.getOmelets());
            }
        }
        return mCachedOmelets;
    }

    @Override
    public void searchForDishesAsync(@NonNull ExecutionCallback<Omelet.OmeletDTO> callback, @NonNull String dishName) {
        mRemoteDataSource.searchForDishesAsync(new ExecutionCallback<OmeletAPI>() {

            @Override
            public void onOmeletsLoaded(List<OmeletAPI> omelets) {
                List<Omelet.OmeletDTO> dishes;
                if (omelets.isEmpty()) {
                    dishes = mCachedOmelets;
                } else {
                    dishes = Converters.convertToCachedOmelets(omelets);
                }
                executeOnMainThread(() -> callback.onOmeletsLoaded(dishes));
            }

            @Override
            public void onDataNotAvailable() {
                mLocalDataSource.searchForDishesAsync(new ExecutionCallback<OmeletDB>() {
                    @Override
                    public void onOmeletsLoaded(List<OmeletDB> omelets) {
                        List<Omelet.OmeletDTO> dishes = Converters.convertToCachedOmelets(omelets);
                        executeOnMainThread(() -> callback.onOmeletsLoaded(dishes));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                }, dishName);
            }
        }, dishName);
    }

    @Override
    public List<Omelet.OmeletDTO> searchForDishes(@NonNull String dishName) {
        if (TextUtils.isEmpty(dishName)) {
            return mCachedOmelets;
        }
        List<? extends Omelet> omelets = mRemoteDataSource.searchForDishes(dishName);
        if (omelets.isEmpty()) {
            omelets = mLocalDataSource.searchForDishes(dishName);
        }
        return Converters.convertToCachedOmelets(omelets);
    }

    private void preCacheRemoteOmelets(@NonNull ExecutionCallback<Omelet.OmeletDTO> callback) {
        mRemoteDataSource.getOmeletsAsync(new ExecutionCallback<OmeletAPI>() {
            @Override
            public void onOmeletsLoaded(List<OmeletAPI> omelets) {
                mCachedOmelets = saveOmeletsLocally(omelets);
                executeOnMainThread(() -> callback.onOmeletsLoaded(mCachedOmelets));
            }

            @Override
            public void onDataNotAvailable() {
            }
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

    private Executor executeOnMainThread(Runnable runnable) {
        Executor uiExecutor = getAppExecutors().getExecutor(AppExecutors.UI);
        uiExecutor.execute(runnable);
        return uiExecutor;
    }
}
