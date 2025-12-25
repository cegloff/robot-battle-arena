package com.example.robotbattle.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.robotbattle.R
import com.example.robotbattle.data.SavedRobot

class SavedRobotAdapter(private val onLoad: (Long) -> Unit) : ListAdapter<SavedRobot, SavedRobotViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedRobotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_robot, parent, false)
        return SavedRobotViewHolder(view, onLoad)
    }

    override fun onBindViewHolder(holder: SavedRobotViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DiffCallback : DiffUtil.ItemCallback<SavedRobot>() {
        override fun areItemsTheSame(oldItem: SavedRobot, newItem: SavedRobot) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SavedRobot, newItem: SavedRobot) = oldItem == newItem
    }
}

class SavedRobotViewHolder(itemView: View, private val onLoad: (Long) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.findViewById(R.id.tv_name)
    private val tvHealth: TextView = itemView.findViewById(R.id.tv_health)
    private val tvDamage: TextView = itemView.findViewById(R.id.tv_damage)
    private val btnDelete: Button = itemView.findViewById(R.id.btn_delete)

    fun bind(robot: SavedRobot) {
        tvName.text = robot.name
        tvHealth.text = "Health: ${robot.totalHealth}"
        tvDamage.text = "Damage: ${robot.totalDamage}"
        itemView.setOnClickListener { onLoad(robot.id) }
        btnDelete.setOnClickListener { /* TODO: implement delete */ }
    }
}