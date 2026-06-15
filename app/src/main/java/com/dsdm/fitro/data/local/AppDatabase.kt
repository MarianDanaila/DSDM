package com.dsdm.fitro.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dsdm.fitro.data.local.dao.WorkoutDao
import com.dsdm.fitro.data.local.entity.WorkoutEntity

@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "fitro_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
