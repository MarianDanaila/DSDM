package com.dsdm.fitro.data.local.dao

import androidx.room.*
import com.dsdm.fitro.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): Flow<List<WorkoutEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntity)
}
