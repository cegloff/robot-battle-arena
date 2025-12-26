package com.example.robotbattle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.robotbattle.data.AppDatabase
import com.example.robotbattle.data.RobotRepository
import com.example.robotbattle.ui.RobotViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RobotViewModelFactory(private val repository: RobotRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RobotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RobotViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class BuildActivity : AppCompatActivity() {
    private lateinit var viewModel: RobotViewModel
    private lateinit var etName: EditText
    private lateinit var tvParts: TextView
    private val gson = Gson()

    private val importLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            contentResolver.openInputStream(it)?.use { input ->
                val json = input.bufferedReader().use { reader -> reader.readText() }
                try {
                    val importedRobot = gson.fromJson(json, Robot::class.java)
                    updateUI(importedRobot)
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

        val db = AppDatabase.getDatabase(this)
        val repository = RobotRepository(db.robotDao())
        viewModel = viewModels<RobotViewModel> { RobotViewModelFactory(repository) }.value

        val robotId = intent.getLongExtra("robot_id", -1)
        if (robotId != -1L) {
            lifecycleScope.launch {
                viewModel.loadRobot(robotId)
            }
        }

        viewModel.robot.observe(this) { robot ->
            updateUI(robot)
        }

        findViewById<Button>(R.id.btn_import).setOnClickListener {
            importLauncher.launch("application/json")
        }

        findViewById<Button>(R.id.btn_export).setOnClickListener {
            val robot = viewModel.robot.value ?: return@setOnClickListener
            val json = gson.toJson(robot)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, json)
            }
            startActivity(Intent.createChooser(intent, "Export Robot"))
        }

        // TODO: Add part selection UI
    }

    private fun updateUI(robot: Robot) {
        etName.setText(robot.name)
        tvParts.text = "Parts: ${robot.parts.map { it.name }.joinToString(", ")}"
    }
}
