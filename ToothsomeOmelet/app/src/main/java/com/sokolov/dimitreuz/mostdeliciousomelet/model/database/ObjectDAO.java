package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;


public interface ObjectDAO<Object> {

    @Insert
    long[] insertAll(Object... omelets);

    @Delete
    void delete(Object omeleteDB);

    @Update
    void update(Object... objects);
}
