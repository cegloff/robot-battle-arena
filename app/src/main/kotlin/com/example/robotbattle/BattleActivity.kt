package com.example.robotbattle

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BattleActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        gameView = findViewById(R.id.game_view)
        tvStatus = findViewById(R.id.tv_status)

        // Placeholder: load two robots
        val playerParts = mutableListOf(RobotPart("wheel", "Wheel", 50), RobotPart("weapon", "Gun", 20, 10, 50))
        val playerRobot = Robot("Player", playerParts)
        val enemyParts = mutableListOf(RobotPart("wheel", "Wheel", 50), RobotPart("weapon", "Gun", 20, 15, 60))
        val enemyRobot = Robot("Enemy", enemyParts)

        gameView.startBattle(playerRobot, enemyRobot)
    }
}
