package com.example.robotbattle.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RobotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(robot: SavedRobot)

    @Query("SELECT * FROM savedrobot")
    fun getAll(): Flow<List<SavedRobot>>

    @Query("SELECT * FROM savedrobot WHERE id = :id")
    suspend fun getById(id: Long): SavedRobot?

    @Delete
    suspend fun delete(robot: SavedRobot)
}