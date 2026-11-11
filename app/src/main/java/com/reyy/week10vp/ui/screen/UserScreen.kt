package com.reyy.week10vp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reyy.week10vp.util.Resource
import com.reyy.week10vp.viewmodel.UserViewModel

@Composable
fun UserScreen(viewModel: UserViewModel) {
    val state by viewModel.userState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (val result = state) {
            is Resource.Loading -> CircularProgressIndicator()
            is Resource.Error -> Text(text = result.message, color = MaterialTheme.colorScheme.error)
            is Resource.Success -> {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(result.data) { user ->
                        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = user.name, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Company: ${user.companyName}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}