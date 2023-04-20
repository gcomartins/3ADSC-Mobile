package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.saveup.databinding.ActivityCadastroBinding
import com.example.saveup.databinding.ActivityLoginBinding
import models.Usuario
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.UsuarioService

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btSignIn.setOnClickListener {
            login()
        }

        binding.btSignUp.setOnClickListener {
            GoToSignUpPage()
        }


    }

    private fun goToHomePage() {
        val homePage = Intent(this, SemGastos::class.java)
        startActivity(homePage)
    }

    private fun GoToSignUpPage() {
        val signUpPage = Intent(this, Cadastro::class.java)
        startActivity(signUpPage)
    }

    private fun loginValidation(): Boolean {
        return when {
            binding.etEmailLogin.text.isNullOrEmpty() -> {
                binding.etEmailLogin.error = "Email não pode ser nulo ou em branco!"
                return false
            }
            !binding.etEmailLogin.text.contains("@", ignoreCase = true) -> {
                binding.etEmailLogin.error = "Email deve conter domínio!"
                return false
            }
            binding.etSenhaLogin.text.isNullOrEmpty() -> {
                binding.etSenhaLogin.error = "Senha não pode ser nula ou em branco!"
                return false
            }

            else -> true
        }
    }


    private fun login() {
        val email = binding.etEmailLogin.text.toString()
        val senha = binding.etSenhaLogin.text.toString()

        if (loginValidation()) {
            retrofit
                .create(UsuarioService::class.java).login(email, senha)
                .enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        println(response.body())
                        if (response.code() == 200) {
                            Toast.makeText(
                                baseContext,
                                "Login realizado com sucesso!",
                                Toast.LENGTH_LONG
                            ).show()
                            goToHomePage()

                        } else if(response.code() == 404){
                            Toast.makeText(
                                baseContext,
                                "Email ou senha inválidos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else {
                            Toast.makeText(
                                baseContext,
                                "Erro ao realizar o login!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        println(t)
                    }
                })
        }


    }
}

