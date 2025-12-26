package com.example.robotbattle.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.robotbattle.BuildActivity
import com.example.robotbattle.R
import com.example.robotbattle.Robot
import com.example.robotbattle.data.AppDatabase
import com.example.robotbattle.data.RobotRepository
import kotlinx.coroutines.launch

class SavedRobotsActivity : AppCompatActivity() {
    private lateinit var repository: RobotRepository
    private lateinit var adapter: SavedRobotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_robots)

        val db = AppDatabase.getDatabase(this)
        repository = RobotRepository(db.robotDao())

        adapter = SavedRobotAdapter { robotId ->
            val intent = Intent(this, BuildActivity::class.java).apply {
                putExtra("robot_id", robotId)
            }
            startActivity(intent)
        }

        findViewById<RecyclerView>(R.id.recycler_saved_robots).apply {
            layoutManager = LinearLayoutManager(this@SavedRobotsActivity)
            adapter = this@SavedRobotsActivity.adapter
        }

        lifecycleScope.launch {
            repository.loadAll().collect { robots ->
                adapter.submitList(robots)
            }
        }
    }
}