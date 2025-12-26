package com.example.robotbattle.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.robotbattle.PartTemplate
import com.example.robotbattle.Robot
import com.example.robotbattle.ValidationResult
import com.example.robotbattle.data.RobotRepository
import kotlinx.coroutines.launch

class RobotViewModel(private val repository: RobotRepository) : ViewModel() {
    private val _robot = MutableLiveData(Robot("New Robot", mutableListOf()))
    val robot: LiveData<Robot> get() = _robot

    fun addPart(template: PartTemplate) {
        val current = _robot.value ?: return
        current.parts.add(template.toPart())
        _robot.value = current
    }

    fun removePart(index: Int) {
        val current = _robot.value ?: return
        if (index in current.parts.indices) {
            current.parts.removeAt(index)
            _robot.value = current
        }
    }

    fun validate(): ValidationResult {
        val current = _robot.value ?: return ValidationResult.Error("No robot")
        return current.validate()
    }

    suspend fun loadRobot(id: Long) {
        val loaded = repository.loadById(id)
        if (loaded != null) {
            _robot.value = loaded
        }
    }

    fun saveRobot() {
        val current = _robot.value ?: return
        viewModelScope.launch {
            repository.saveOrUpdate(current)
        }
    }
}