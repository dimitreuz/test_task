package com.sokolov.dimitreuz.mostdeliciousomelet.model.database;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;


public interface ObjectDAO<Object> {

    @Insert
    long[] insertAll(@NonNull Object... omelets);

    @Delete
    void delete(@NonNull Object omeleteDB);

    @Update
    void update(@NonNull Object... objects);
}
