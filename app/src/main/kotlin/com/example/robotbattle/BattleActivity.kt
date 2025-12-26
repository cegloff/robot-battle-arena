package com.example.robotbattle

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class BattleActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private lateinit var tvStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle)

        val robotJson = intent.getStringExtra("robot")
        val playerRobot = if (robotJson != null) {
            Gson().fromJson(robotJson, Robot::class.java)
        } else {
            Robot("Player", mutableListOf(RobotPart("wheel", "Wheel", 50), RobotPart("weapon", "Gun", 20, 10, 50)))
        }

        if (playerRobot.validate() is ValidationResult.Error) {
            Toast.makeText(this, "Invalid robot", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        gameView = findViewById(R.id.game_view)
        tvStatus = findViewById(R.id.tv_status)

        // Placeholder: load two robots
        val enemyParts = mutableListOf(RobotPart("wheel", "Wheel", 50), RobotPart("weapon", "Gun", 20, 15, 60))
        val enemyRobot = Robot("Enemy", enemyParts)

        gameView.startBattle(playerRobot, enemyRobot)
    }
}
