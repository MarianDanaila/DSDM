package com.dsdm.fitro.repository

import com.dsdm.fitro.data.local.dao.WorkoutDao
import com.dsdm.fitro.data.local.entity.WorkoutEntity
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    fun getAllWorkouts(): Flow<List<WorkoutEntity>> = workoutDao.getAllWorkouts()

    suspend fun insertWorkout(workout: WorkoutEntity) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: WorkoutEntity) {
        workoutDao.deleteWorkout(workout)
    }
}
