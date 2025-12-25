package com.example.robotbattle

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.robotbattle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBuild.setOnClickListener {
            startActivity(Intent(this, BuildActivity::class.java))
        }

        binding.btnBattle.setOnClickListener {
            startActivity(Intent(this, BattleActivity::class.java))
        }
    }
}
