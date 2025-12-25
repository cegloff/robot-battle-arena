package com.example.robotbattle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import java.io.BufferedReader

class BuildActivity : AppCompatActivity() {
    private lateinit var robot: Robot
    private lateinit var etName: EditText
    private lateinit var tvParts: TextView
    private val gson = Gson()

    private val importLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            contentResolver.openInputStream(it)?.use { input ->
                val json = input.bufferedReader().use { reader -> reader.readText() }
                try {
                    robot = gson.fromJson(json, Robot::class.java)
                    updateUI()
                    Toast.makeText(this, "Robot imported", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Invalid robot file", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build)

        etName = findViewById(R.id.et_robot_name)
        tvParts = findViewById(R.id.tv_parts)
        robot = Robot("New Robot", mutableListOf())

        findViewById<Button>(R.id.btn_add_wheel).setOnClickListener {
            robot.parts.add(RobotPart("wheel", "Basic Wheel", health = 50, speed = 5))
            updateUI()
        }

        findViewById<Button>(R.id.btn_add_weapon).setOnClickListener {
            robot.parts.add(RobotPart("weapon", "Laser", health = 20, damage = 30, range = 100))
            updateUI()
        }

        findViewById<Button>(R.id.btn_import).setOnClickListener {
            importLauncher.launch("application/json")
        }

        findViewById<Button>(R.id.btn_export).setOnClickListener {
            robot.name = etName.text.toString().ifEmpty { robot.name }
            val json = gson.toJson(robot)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, json)
            }
            startActivity(Intent.createChooser(intent, "Export Robot"))
        }
    }

    private fun updateUI() {
        etName.setText(robot.name)
        tvParts.text = "Parts: ${robot.parts.map { it.name }.joinToString(", ")}"
    }
}
