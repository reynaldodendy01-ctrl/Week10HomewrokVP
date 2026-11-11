package com.reyy.week10vp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reyy.week10vp.data.repository.UserRepository
import com.reyy.week10vp.model.User
import com.reyy.week10vp.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val userState: StateFlow<Resource<List<User>>> = _userState

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            _userState.value = repository.getUsers()
        }
    }
}