package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote;

import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.AbstractOmeletDataSource;

import java.io.IOException;
import java.util.List;

public class RemoteOmeletDataSource extends AbstractOmeletDataSource<OmeletAPI> {

    private OmeletsAPI mOmeletsAPI;

    public RemoteOmeletDataSource(AppExecutors appExecutors) {
        super(appExecutors);
        mOmeletsAPI = OmeletsAPI.getInstance();
    }

    @Override
    public void getOmelets(@NonNull ExecutionCallback<OmeletAPI> callback) {
        Runnable r = () -> {
            try {
                List<OmeletAPI> omeletes = getApiService().getAllOmelet().execute().body();
                callback.onOmeletsLoaded(omeletes);
            } catch (IOException e) {
                callback.onDataNotAvailable();
            }
        };

    }

    public OmeletsAPI.ApiService getApiService() {
        return mOmeletsAPI.getApiService();
    }
}
