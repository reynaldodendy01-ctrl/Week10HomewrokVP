package com.reyy.week10vp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reyy.week10vp.ui.screen.UserScreen
import com.reyy.week10vp.viewmodel.UserViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    NavHost(navController = navController, startDestination = Routes.UserList.route) {
        composable(Routes.UserList.route) {
            UserScreen(viewModel = userViewModel)
        }
    }
}