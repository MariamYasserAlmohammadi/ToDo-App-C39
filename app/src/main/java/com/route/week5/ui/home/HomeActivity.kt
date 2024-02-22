package com.route.week5.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.route.week5.R
import com.route.week5.database.dao.MyDataBase
import com.route.week5.databinding.ActivityHomeBinding
import com.route.week5.ui.home.adddTask.AddTaskBottomSheet
import com.route.week5.ui.home.settings.SettingsFragment
import com.route.week5.ui.home.tasksList.TasksListFragment

// Room persistence library provide an abstraction layer over SQL
// 1.Compile time verification of SQL queries
// 2.KAPT Kotlin Annotations Processor , @...
// 3.support database migration paths
// DAO -> data access object

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun showAddTaskBottomSheet() {
        val addTaskSheet = AddTaskBottomSheet()
        addTaskSheet.onTaskAddedListener =
            AddTaskBottomSheet.OnTaskAddedListener {
                // notify tasksListFragment
                supportFragmentManager.fragments.forEach { fragment ->
                    if (fragment is TasksListFragment && fragment.isAdded) {
                        fragment.retrieveTasksList()
                    }
                }
            }
        addTaskSheet.show(supportFragmentManager, null)

    }
    // initiate views
    private fun setUpViews() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_tasks) {
                showFragment(TasksListFragment())
            } else {
                showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigation.selectedItemId = R.id.navigation_tasks
        binding.fabAddTask.setOnClickListener {
            showAddTaskBottomSheet()
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}