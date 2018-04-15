package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.dto.OmeleteDB;


@Database(entities = {OmeleteDB.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OmeletDAO getOmeletDAO();
}
