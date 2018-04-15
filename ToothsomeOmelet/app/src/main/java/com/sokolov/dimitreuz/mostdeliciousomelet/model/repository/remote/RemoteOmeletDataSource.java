package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.remote;

import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.api.OmeletsAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeletAPI;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.repository.OmeletDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteOmeletDataSource implements OmeletDataSource<OmeletAPI> {

    private OmeletsAPI mOmeletsAPI;

    public RemoteOmeletDataSource() {
        mOmeletsAPI = OmeletsAPI.getInstance();
    }

    @Override
    public List<OmeletAPI> getOmelets(@NonNull ExecutionCallback callback) {
        try {
            return getApiService().getAllOmelet().execute().body();
        } catch (IOException e) {
            callback.onDataNotAvailable();
            return new ArrayList<>();
        }
    }

    public OmeletsAPI.ApiService getApiService() {
        return mOmeletsAPI.getApiService();
    }
}
