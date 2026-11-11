package com.reyy.week10vp.data.repository

import com.reyy.week10vp.data.dto.UserDto
import com.reyy.week10vp.data.remote.UserApiService
import com.reyy.week10vp.model.User
import com.reyy.week10vp.util.Resource

class UserRepository(private val apiService: UserApiService) {
    suspend fun getUsers(): Resource<List<User>> {
        return try {
            val response = apiService.getUsers()
            // Mapping dari UserDto ke Model User UI
            val domainUsers = response.map { dto ->
                User(
                    id = dto.id,
                    name = dto.name,
                    email = dto.email,
                    companyName = dto.company?.companyName ?: "Tidak ada perusahaan"
                )
            }
            Resource.Success(domainUsers)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Gagal mengambil data dari internet")
        }
    }
}