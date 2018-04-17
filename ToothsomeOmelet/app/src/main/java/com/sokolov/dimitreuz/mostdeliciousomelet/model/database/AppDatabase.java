package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.sokolov.dimitreuz.mostdeliciousomelet.model.DTO.OmeletDB;


@Database(entities = {OmeletDB.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "omelets-database";

    private static AppDatabase mInstance;

    private static final Object LOCKER = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCKER) {
            if (mInstance == null) {
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        DATABASE_NAME
                ).fallbackToDestructiveMigration()
                 .build();
            }
            return mInstance;
        }
    }

    public abstract OmeletDAO getOmeletDAO();
}
