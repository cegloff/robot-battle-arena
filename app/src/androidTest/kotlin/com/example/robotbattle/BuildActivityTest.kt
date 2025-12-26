package com.example.robotbattle

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BuildActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(BuildActivity::class.java)

    @Test
    fun testCreateAndSaveFlow() {
        // TODO: Implement Espresso tests
    }

    @Test
    fun testLoadAndEdit() {
        // TODO: Implement
    }

    @Test
    fun testValidationBlocksBattle() {
        // TODO: Implement
    }
}