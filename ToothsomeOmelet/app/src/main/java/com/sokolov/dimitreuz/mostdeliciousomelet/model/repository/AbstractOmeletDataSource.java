package com.sokolov.dimitreuz.mostdeliciousomelet.model.repository;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.AppExecutors;
import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.Omelet;

public abstract class AbstractOmeletDataSource<T extends Omelet> implements OmeletDataSource<T> {

    private final AppExecutors mAppExecutors;

    public AbstractOmeletDataSource(AppExecutors appExecutors) {
        this.mAppExecutors = appExecutors;
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
