package com.blogspot.android_czy_java.beautytips.database.error

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel

@Dao
interface ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertError(error: ErrorModel)

}