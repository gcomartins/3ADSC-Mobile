package com.example.saveup

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.saveup.databinding.ActivityTelaPrincipalBinding

class TelaPrincipalActivity : AppCompatActivity() {
    private val binding by lazy {
        println(layoutInflater)
        ActivityTelaPrincipalBinding.inflate(layoutInflater)
    }

    private val navController by lazy {
        (supportFragmentManager
            .findFragmentById(binding.fragmentContainerView.id) as NavHostFragment)
            .navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNavigation.setupWithNavController(navController)

        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainerView)

//        val meuFragment = FragmentObjetivo()
//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerView, meuFragment)
//            .commit()

    }
}