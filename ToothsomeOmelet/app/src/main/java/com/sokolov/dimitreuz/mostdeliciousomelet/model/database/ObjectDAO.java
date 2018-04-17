package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

/**
 * Current class is used as base data access object
 * for model in current domain
 *
 * Current interface contains default CRUD functions
 * to interact with local database.
 *
 * All methods annotated with Room annotaions to provide
 * code generation for each method.
 *
 * @param <Object> - represents required object`s domain class.
 */
public interface ObjectDAO<Object> {

    /**
     * Current method is used to insert models
     * to database.
     *
     * @param omelets array of models required to insert.
     * @return instances`s id of inserted model.
     */
    @Insert
    long[] insertAll(@NonNull Object... omelets);

    /**
     * Current method is used to delete required model
     * from database.
     *
     * @param omeletDB - instances required to delete.
     */
    @Delete
    void delete(@NonNull Object omeletDB);

    /**
     * Current method is used to update required models
     * in database.
     *
     * @param objects array of models required to update.
     */
    @Update
    void update(@NonNull Object... objects);
}
