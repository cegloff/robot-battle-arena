package com.example.robotbattle.data

import com.example.robotbattle.Robot
import com.google.gson.Gson

class RobotRepository(private val dao: RobotDao, private val gson: Gson = Gson()) {
    suspend fun saveOrUpdate(robot: Robot) {
        val partsJson = gson.toJson(robot.parts)
        val savedRobot = SavedRobot(
            id = robot.id ?: 0,
            name = robot.name,
            partsJson = partsJson,
            totalHealth = robot.totalHealth(),
            totalDamage = robot.totalDamage(),
            createdAt = System.currentTimeMillis()
        )
        dao.insert(savedRobot)
        robot.id = savedRobot.id
    }

    fun loadAll(): Flow<List<SavedRobot>> = dao.getAll()

    suspend fun loadById(id: Long): Robot? {
        val saved = dao.getById(id) ?: return null
        val parts = gson.fromJson(saved.partsJson, Array<RobotPart>::class.java).toList()
        return Robot(saved.name, parts.toMutableList()).apply { this.id = saved.id }
    }

    suspend fun delete(id: Long) {
        val saved = dao.getById(id) ?: return
        dao.delete(saved)
    }
}