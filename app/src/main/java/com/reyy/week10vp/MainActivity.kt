package com.reyy.week10vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.reyy.week10vp.data.remote.ApiConfig
import com.reyy.week10vp.data.repository.UserRepository
import com.reyy.week10vp.navigation.AppNavHost
import com.reyy.week10vp.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = UserRepository(ApiConfig.getApiService())
        val userViewModel = UserViewModel(repository)

        setContent {
            val navController = rememberNavController()
            AppNavHost(
                navController = navController,
                userViewModel = userViewModel
            )
        }
    }
}