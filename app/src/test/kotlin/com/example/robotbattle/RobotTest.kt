package com.example.robotbattle

import org.junit.Assert.*
import org.junit.Test

class RobotTest {

    @Test
    fun testRobotCreation() {
        val part = RobotPart("wheel", "Test Wheel", health = 50, speed = 5)
        val robot = Robot("Test Robot", mutableListOf(part))
        assertEquals("Test Robot", robot.name)
        assertEquals(1, robot.parts.size)
        assertEquals("Test Wheel", robot.parts[0].name)
    }

    @Test
    fun testTotalHealth() {
        val parts = mutableListOf(
            RobotPart("wheel", "Wheel", 50),
            RobotPart("weapon", "Gun", 20)
        )
        val robot = Robot("Test", parts)
        assertEquals(70, robot.totalHealth())
    }

    @Test
    fun testTotalDamage() {
        val parts = mutableListOf(
            RobotPart("weapon", "Gun", damage = 10),
            RobotPart("weapon", "Laser", damage = 20)
        )
        val robot = Robot("Test", parts)
        assertEquals(30, robot.totalDamage())
    }

    // Add more tests for coverage
    @Test
    fun testEmptyRobot() {
        val robot = Robot("Empty")
        assertEquals(0, robot.totalHealth())
        assertEquals(0, robot.totalDamage())
    }
}
