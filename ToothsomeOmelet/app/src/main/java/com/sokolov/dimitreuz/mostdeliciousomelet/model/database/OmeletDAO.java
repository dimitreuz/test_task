package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;

import java.util.List;

/**
 * Current interface provides data access object
 * specially for omelet instance.
 */
@Dao
public interface OmeletDAO extends ObjectDAO<OmeletDB> {

    /**
     * Current method is used to get all stored instances
     * in database.
     *
     * @return list of all stored instances.
     */
    @Query("SELECT * FROM omelet")
    List<OmeletDB> getAll();

    /**
     * Current method provides list of omelets filtered by title.
     * (When we would like to search instances in offline mode).
     * @param dishName - name(title) of dish.
     *
     * @return list of instances filtered by title
     */
    @Query("SELECT * FROM omelet WHERE omelet.title LIKE ('%' || :dishName || '%')")
    List<OmeletDB> findRequiredOmelets(@NonNull String dishName);
}
