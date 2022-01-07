package com.example.kotlin.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.eho.data.local.db.dao.DummyDao
import com.app.eho.data.local.db.entity.DummyEntity

/*
define database information like tables, version,
 */

@Database(
    entities = [
        DummyEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class DatabaseService : RoomDatabase() {

    abstract fun dummyDao(): DummyDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DatabaseService? = null

        fun getDatabase(context: Context): DatabaseService {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseService::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}