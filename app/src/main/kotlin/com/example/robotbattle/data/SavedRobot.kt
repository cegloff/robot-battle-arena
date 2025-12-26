package com.example.robotbattle.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
tableName "savedrobot"
data class SavedRobot(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val partsJson: String,
    val totalHealth: Int,
    val totalDamage: Int,
    val createdAt: Long
)