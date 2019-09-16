package com.blogspot.android_czy_java.beautytips.repository.forViewModels.error

import com.blogspot.android_czy_java.beautytips.database.error.ErrorDao
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel

class ErrorRepository(private val errorDao: ErrorDao) {

    fun saveError(error: ErrorModel) {
        if (errorDao.queryError(error.tableName, error.rowId) == null)
            errorDao.insertError(error)
    }

    fun deleteError(error: ErrorModel) {
        errorDao.deleteError(error.tableName, error.rowId)
    }

    fun getError(error: ErrorModel) =
            errorDao.queryError(error.tableName, error.rowId)


}