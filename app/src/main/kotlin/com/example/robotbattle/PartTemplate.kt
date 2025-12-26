package com.example.robotbattle

data class PartTemplate(
    val type: String,
    val name: String,
    val health: Int,
    val damage: Int? = null,
    val speed: Int? = null,
    val range: Int? = null
) {
    fun toPart(): RobotPart {
        return RobotPart(type, name, health, damage ?: 0, range ?: 0, speed ?: 0)
    }
}