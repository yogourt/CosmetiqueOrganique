package com.blogspot.android_czy_java.beautytips.database.error

import androidx.room.*
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel

@Dao
interface ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertError(error: ErrorModel)

    @Query("DELETE FROM Errors WHERE tableName=:tableName AND rowId=:rowId")
    fun deleteError(tableName: String, rowId: String)

    @Query("SELECT * FROM Errors WHERE tableName=:tableName AND rowId=:rowId")
    fun queryError(tableName: String, rowId: String): ErrorModel?

    @Query("SELECT * FROM Errors WHERE tableName = :tableName")
    fun getAllErrorsFromTable(tableName: String): List<ErrorModel>?

}