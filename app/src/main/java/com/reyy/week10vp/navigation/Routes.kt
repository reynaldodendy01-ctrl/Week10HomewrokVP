package com.reyy.week10vp.navigation

sealed class Routes(val route: String) {
    object UserList : Routes("user_list")
}