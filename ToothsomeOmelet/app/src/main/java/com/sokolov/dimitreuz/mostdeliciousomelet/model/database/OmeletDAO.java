package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;

import java.util.List;

@Dao
public interface OmeletDAO extends ObjectDAO<OmeletDB> {

    @Query("SELECT * FROM OmeletDB")
    public List<OmeletDB> getAll();

}
