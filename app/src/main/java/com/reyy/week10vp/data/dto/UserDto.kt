package com.reyy.week10vp.data.dto

import com.google.gson.annotations.SerializedName

// Mengambil response JSON dari https://jsonplaceholder.typicode.com/users
data class UserDto(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("company")
    val company: CompanyDto?
)

data class CompanyDto(
    @field:SerializedName("name")
    val companyName: String)