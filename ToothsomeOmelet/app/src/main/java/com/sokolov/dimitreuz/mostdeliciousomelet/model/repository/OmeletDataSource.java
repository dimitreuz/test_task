package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;

import java.util.List;

public interface OmeletDataSource<T extends Omelet> {

    interface ExecutionCallback<T extends Omelet> {

        void onOmeletsLoaded(List<T> omelets);

        void onDataNotAvailable();
    }

    void getOmelets(ExecutionCallback<T> callback);
}
