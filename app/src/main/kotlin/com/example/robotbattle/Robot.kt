package com.example.robotbattle

data class RobotPart(
    val type: String,
    val name: String,
    val health: Int = 0,
    val damage: Int = 0,
    val range: Int = 0,
    val speed: Int = 0
)

data class Robot(
    val name: String,
    val parts: List<RobotPart>
) {
    fun totalHealth(): Int = parts.sumOf { it.health }

    fun totalDamage(): Int = parts.sumOf { it.damage }

    // Add more methods for simulation
}
