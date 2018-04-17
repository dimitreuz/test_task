package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPIHolder;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;

public class RemoteOmeletDataSource extends AbstractOmeletDataSource<OmeletAPI> {

    private OmeletsAPI mOmeletsAPI;

    public RemoteOmeletDataSource(AppExecutors appExecutors) {
        super(appExecutors);
        mOmeletsAPI = OmeletsAPI.getInstance();
    }

    @Override
    public void getOmeletsAsync(@NonNull ExecutionCallback<OmeletAPI> callback) {
        getAppExecutors().getExecutor(AppExecutors.NET).execute(() -> {
            List<OmeletAPI> omelets = getOmelets();
            if (omelets.isEmpty()) {
                callback.onDataNotAvailable();
            } else {
                callback.onOmeletsLoaded(omelets);
            }
        });
    }

    @Override
    public List<OmeletAPI> getOmelets() {
        Call<OmeletsAPIHolder> call = getApiService().getAllOmelet();
        try {
            return call.execute().body().getRequiredOmelets();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void searchForDishesAsync(@NonNull ExecutionCallback<OmeletAPI> callback, @NonNull String dishName) {
        Executor executor = getAppExecutors().getExecutor(AppExecutors.NET);
        executor.execute(() -> {
            if (!TextUtils.isEmpty(dishName)) try {
                List<OmeletAPI> omelets = searchForDishes(dishName);
                Thread.sleep(10);
                if (omelets.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onOmeletsLoaded(omelets);
                }
            } catch (InterruptedException e) { /* SUBMIT CANCEL OF FUTURE */ }
        });
    }

    @Override
    public List<OmeletAPI> searchForDishes(@NonNull String dishName) {
        Call<OmeletsAPIHolder> call = getApiService().getSearchedOmelets(dishName);
        try {
            return call.execute().body().getRequiredOmelets();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public OmeletsAPI.ApiService getApiService() {
        return mOmeletsAPI.getApiService();
    }
}
