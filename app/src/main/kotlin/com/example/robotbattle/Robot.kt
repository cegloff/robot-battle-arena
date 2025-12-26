package com.example.robotbattle

data class RobotPart(
    val type: String,
    val name: String,
    val health: Int = 0,
    val damage: Int = 0,
    val range: Int = 0,
    val speed: Int = 0
)

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

class Robot(var name: String, var parts: MutableList<RobotPart> = mutableListOf()) {
    var id: Long? = null
    fun totalHealth(): Int = parts.sumOf { it.health }

    fun totalDamage(): Int = parts.sumOf { it.damage }

    fun validate(): ValidationResult {
        if (parts.size > 6) {
            return ValidationResult.Error("Cannot have more than 6 parts")
        }
        if (parts.none { it.type == "mobility" }) {
            return ValidationResult.Error("Must have at least one mobility part")
        }
        return ValidationResult.Success
    }

    companion object {
        val PART_TEMPLATES = listOf(
            PartTemplate("mobility", "Wheels", health = 50, speed = 5),
            PartTemplate("mobility", "Treads", health = 80, speed = 3),
            PartTemplate("mobility", "Legs", health = 60, speed = 4),
            PartTemplate("weapon", "Laser", health = 20, damage = 15, range = 5),
            PartTemplate("weapon", "Cannon", health = 30, damage = 25, range = 3),
            PartTemplate("armor", "Shield", health = 100),
            PartTemplate("sensor", "Radar", health = 10, range = 10)
        )
    }
}
