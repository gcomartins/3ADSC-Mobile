package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
//import androidx.navigation.Navigator
import com.example.saveup.databinding.ActivityTelaInicialBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelaInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

<<<<<<< HEAD
       val signUpPage = Intent(this, TelaPrincipalActivity::class.java)
       startActivity(signUpPage)
=======
        val signUpPage = Intent(this, Login::class.java)
        startActivity(signUpPage)
>>>>>>> 2f9dab7508287b4c100ffc68f144746952998f52

        binding.btSignUp.setOnClickListener {
            GoToSignUpPage()
        }
    }

    private fun GoToSignUpPage() {
        val signUpPage = Intent(this, Cadastro::class.java)
        startActivity(signUpPage)
    }
}