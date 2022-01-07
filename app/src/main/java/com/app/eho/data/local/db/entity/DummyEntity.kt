package com.app.eho.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/*
@Entity - define table name
@ColumnInfo - define column name. otherwise it will take variable as a column name
 */

/*
data class:
 - Declaring a data class must contains at least one primary constructor with property argument (val or var).
 - "Data class internally contains the following functions:

     equals(): Boolean
     hashCode(): Int
     toString(): String
     component() functions corresponding to the properties
     copy()
Due to presence of above functions internally in data class, the data class eliminates the boilerplate code."
 */

@Entity(tableName = "dummy_entity")
data class DummyEntity(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    val id: Long,

    @ColumnInfo(name = "name")
    @NotNull
    val name: String
)