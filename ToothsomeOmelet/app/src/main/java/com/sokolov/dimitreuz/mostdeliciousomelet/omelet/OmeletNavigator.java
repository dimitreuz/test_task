package com.sokolov.dimitreuz.mostdeliciousomelet.omelet;

import android.support.annotation.Nullable;

/**
 * Current interface is used for handling
 * logic of omelet item click event.
 */
public interface OmeletNavigator {

    /**
     * Current methods provide business logic
     * describe in requirements.
     *
     * @param href - link to web-site where
     * omelet`s recipe described.
     */
    void onOmeletItemClick(@Nullable String href);
}
