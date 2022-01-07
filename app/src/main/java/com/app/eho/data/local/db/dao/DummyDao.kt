package com.app.eho.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.eho.data.local.db.entity.DummyEntity

/*
Data Access objects:
 define query to access data (add/update/delete)from table(entity)
*/

@Dao
interface DummyDao {

    @Query("SELECT * FROM dummy_entity")
    fun getAll(): List<DummyEntity>

    @Insert
    fun insert(entity: DummyEntity)

    @Delete
    fun delete(entity: DummyEntity)
}