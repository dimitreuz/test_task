package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;

import java.util.List;

@Dao
public interface OmeletDAO extends ObjectDAO<OmeletDB> {

    @Query("SELECT * FROM omelet")
    List<OmeletDB> getAll();

    @Query("SELECT * FROM omelet WHERE omelet.title LIKE ('%' || :dishName || '%')")
    List<OmeletDB> findRequiredOmelets(@NonNull String dishName);
}
