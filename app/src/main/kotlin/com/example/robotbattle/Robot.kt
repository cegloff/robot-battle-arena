package com.example.robotbattle

data class RobotPart(
    val type: String,
    val name: String,
    val health: Int = 0,
    val damage: Int = 0,
    val range: Int = 0,
    val speed: Int = 0
)

class Robot(var name: String, var parts: MutableList<RobotPart> = mutableListOf()) {
    fun totalHealth(): Int = parts.sumOf { it.health }

    fun totalDamage(): Int = parts.sumOf { it.damage }

    // Add more methods for simulation
}
