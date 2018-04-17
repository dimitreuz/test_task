package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPIHolder;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;

import java.io.IOException;
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
    public void getOmelets(@NonNull ExecutionCallback<OmeletAPI> callback) {
        getAppExecutors().getExecutor(AppExecutors.NET).execute(() -> {
            try {
                Call<OmeletsAPIHolder> call = getApiService().getAllOmelet();
                List<OmeletAPI> omelets = call.execute().body().getRequiredOmelets();
                callback.onOmeletsLoaded(omelets);
            } catch (IOException e) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public Executor searchForOmelets(@NonNull ExecutionCallback<OmeletAPI> callback, @NonNull String dishName) {
        Executor executor = getAppExecutors().getExecutor(AppExecutors.NET);
        executor.execute(() -> {
            try {
                Call<OmeletsAPIHolder> call = getApiService().getSearchedOmelets(dishName);
                List<OmeletAPI> omelets = call.execute().body().getRequiredOmelets();
                Thread.sleep(10);
                callback.onOmeletsLoaded(omelets);
            } catch (IOException e) {
                callback.onDataNotAvailable();
            } catch (InterruptedException e) {
                Log.e("THREAD","INTERRUPTED");
            }
        });
        return executor;
    }

    public OmeletsAPI.ApiService getApiService() {
        return mOmeletsAPI.getApiService();
    }
}
