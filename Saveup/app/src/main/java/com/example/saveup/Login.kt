package com.example.saveup

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveup.databinding.ActivityLoginBinding
import models.Usuario
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.UsuarioService
import java.util.*

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.etEmailLogin.text = Editable.Factory.getInstance().newEditable("guilherme.teste@email.com")
        binding.etSenhaLogin.text = Editable.Factory.getInstance().newEditable("Senha@123")

        binding.btSignIn.setOnClickListener {
            login()
        }

        binding.btSignUp.setOnClickListener {
            GoToSignUpPage(this.baseContext)
        }


    }

    private fun goToHomePage() {
        val homePage = Intent(this, TelaPrincipalActivity::class.java)
        startActivity(homePage)
    }

    fun GoToSignUpPage(context: Context) {
        val signUpPage = Intent(context, Cadastro::class.java)
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
    private fun formateDate(date: Date?): String{
        if (date == null) return ""
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dataFormatada = dateFormat.format(date)
        return  dataFormatada
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
                            val dataDenascimento: Date? = response.body()?.dataNascimento
                            val dataFormatada: String = formateDate(dataDenascimento)
                            USUARIO.id = response.body()?.id
                            USUARIO.cpf = response.body()?.cpf
                            USUARIO.email = response.body()?.email
                            USUARIO.nome = response.body()?.nome
                            USUARIO.senha = response.body()?.senha
                            USUARIO.dataNascimento = dataFormatada
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
                        Toast.makeText(
                            baseContext,
                            "Servico indisponível!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }


    }
}

