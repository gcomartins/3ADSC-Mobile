package com.example.saveup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.saveup.databinding.ActivityCadastroBinding
import models.Usuario
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import service.UsuarioService
import java.util.Date

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnSignUp.setOnClickListener { getAllUsers() }
        binding.btSignIn.setOnClickListener {
            GoToSignInPage()
        }
    }

    private fun cadastrar(){
        val password: String = binding.etPassword.text.toString()
        val confirmPassword: String = binding.etConfirmYourPassword.text.toString()
        if(password != confirmPassword){
            binding.etPassword.error = "Sua senha está diferente nos campos Senha e Confirme sua senha"
            return
        }
        val email: String = binding.etEmail.text.toString()
        val senha: String = binding.etPassword.text.toString()
        val nome: String = binding.etName.text.toString()
        val dataDeNascimento: String = binding.etBirthdate.text.toString()
        val cpf: String = binding.etCpf.text.toString()

        retrofit
            .create(UsuarioService::class.java)
            .cadastrar(Usuario(email, nome, senha, dataDeNascimento, cpf))
    }

    private fun getAllUsers(){
        retrofit
            .create(UsuarioService::class.java)
            .getAllUsers().enqueue(object: Callback<List<Usuario>>{
                override fun onResponse(
                    call: Call<List<Usuario>>,
                    response: Response<List<Usuario>>
                ) {
                    response.body()?.forEach{
                        print("-".repeat(50))
                        print(it)
                        print("-".repeat(50))
                    }
                }

                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    print(t)
                }

            })

    }

    private fun GoToSignInPage() {
        val signInPage = Intent(this, Login::class.java)
        startActivity(signInPage)
    }
}