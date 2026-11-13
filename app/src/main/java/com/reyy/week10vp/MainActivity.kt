package com.reyy.week10vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.reyy.week10vp.soal1.navigation.AppNavHost as Soal1AppNavHost
import com.reyy.week10vp.soal2.navigation.AppNavHost as Soal2AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Soal1AppNavHost()
            //Soal2AppNavHost()
        }
    }
}