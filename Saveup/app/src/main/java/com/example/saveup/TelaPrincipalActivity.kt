package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.saveup.databinding.ActivityTelaPrincipalBinding

class TelaPrincipalActivity : AppCompatActivity() {
    private val binding by lazy {
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.fragment3) {
                binding.fab.setImageResource(R.drawable.add_objetivo_icon)
            } else {
                binding.fab.setImageResource(R.drawable.add_icon)
            }
        }

        binding.fab.setOnClickListener {
            val currentDestinationId = navController.currentDestination?.id
            if (currentDestinationId == R.id.fragment3) {
                val intent = Intent(this, CriarObjetivoActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this, NovaDespesa::class.java)
                startActivity(intent)
            }
        }


    }
}