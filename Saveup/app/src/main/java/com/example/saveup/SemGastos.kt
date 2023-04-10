package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SemGastos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem_gastos)
        supportActionBar?.hide()
    }
}