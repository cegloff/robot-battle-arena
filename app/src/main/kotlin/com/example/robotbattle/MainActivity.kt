package com.example.robotbattle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.robotbattle.ui.SavedRobotsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_build).setOnClickListener {
            startActivity(Intent(this, BuildActivity::class.java))
        }

        findViewById<Button>(R.id.btn_battle).setOnClickListener {
            startActivity(Intent(this, BattleActivity::class.java))
        }

        findViewById<Button>(R.id.btn_create_new).setOnClickListener {
            startActivity(Intent(this, BuildActivity::class.java))
        }

        findViewById<Button>(R.id.btn_load_saved).setOnClickListener {
            startActivity(Intent(this, SavedRobotsActivity::class.java))
        }
    }
}
