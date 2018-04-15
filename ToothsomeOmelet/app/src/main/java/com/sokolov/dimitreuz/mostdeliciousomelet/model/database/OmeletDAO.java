package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeleteDB;

import java.util.List;

@Dao
public interface OmeletDAO extends ObjectDAO<OmeleteDB> {

    @Query("SELECT * FROM omelet")
    public List<OmeleteDB> getAll();

}
