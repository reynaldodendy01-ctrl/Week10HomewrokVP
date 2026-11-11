package com.reyy.week10vp.model

// Model murni yang dikonsumsi oleh UI (Clean Architecture)
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val companyName: String
)