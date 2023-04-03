package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_senha)
        supportActionBar?.hide();
    }
}