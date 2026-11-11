package com.reyy.week10vp.data.remote

import com.reyy.week10vp.data.dto.UserDto
import retrofit2.http.GET

interface UserApiService {
    // Public API tanpa butuh API Key
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}