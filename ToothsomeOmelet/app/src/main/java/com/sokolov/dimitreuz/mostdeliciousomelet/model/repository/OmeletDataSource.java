package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.Omelet;

import java.util.List;

public interface OmeletDataSource<O extends Omelet> {

    interface ExecutionCallback {

        void onDataNotAvailable();
    }

    interface LoadOmeletCallback {

        void onOmeletesLoaded(List<Omelet> omelets);
    }

    interface GetOmeletesCallback {

        void onSavedOmeletsLoaded(List<Omelet> omelets);
    }

    List<O> getOmelets(ExecutionCallback callback);
}
