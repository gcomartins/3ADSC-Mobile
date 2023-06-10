package com.example.saveup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveup.databinding.ActivityCadastroBinding
import models.Usuario
import models.UsuarioCadastro
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.UsuarioService
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private val retrofit = Rest.getInstance()
    private lateinit var email: String
    private lateinit var nome: String
    private lateinit var dataDeNascimento: String
    private lateinit var cpf: String
    private lateinit var senha: String
    private lateinit var senhaConfirmada : String
    val autenticado : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnSignUp.setOnClickListener { view -> cadastrar(view) }
        binding.btSignIn.setOnClickListener {
            GoToSignInPage()
        }
    }

    private fun cadastrar(view: View) {
         email= binding.etEmail.text.toString()
         nome= binding.etName.text.toString()
         dataDeNascimento = binding.etBirthdate.text.toString()
         cpf= binding.etCpf.text.toString()
         senha = binding.etPassword.text.toString()
        senhaConfirmada = binding.etConfirmYourPassword.text.toString()
        val isOk : Boolean = when{
            !valideCheckBox () ->  false
            !validarNome() ->  false
            !validarIdade () ->  false
            !validarEmail ()->  false
            !validarSenha ()->  false

            else -> true
        }
               if (!isOk) return
        if (!getAllUsers()) return
       cadastrarApi()
    }

    private fun valideCheckBox(): Boolean{
        val isOk: Boolean = binding.cbPolicyTerms.isChecked
        if (!isOk) {
            val mensagem: String =
                """Para prosseguir você precisará ler e concordar com os nossos termos de privacidade"""
            Toast.makeText(this.baseContext, mensagem, Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
    private fun validarNome(): Boolean {
        if (nome.trim().length < 2) {
            binding.etName.error = "Nome invalido"
            return false
        }
        return true
    }



    private fun validarIdade(): Boolean {
        val dataFormatada = SimpleDateFormat("dd/MM/yyyy")
        try {
            val dataFinal = dataFormatada.parse(dataDeNascimento)
            val hoje: Date = Date()
            val diferenca = TimeUnit.DAYS.convert(hoje.time - dataFinal.time, TimeUnit.MILLISECONDS)
            if (diferenca > 6573) return true
            else {
                binding.etBirthdate.error =
                    "Você precisa ter mais de 18 anos para acessar nossos " +
                            "serviços"
                return false
            }
        } catch (dataInvalida: Exception) {
            binding.etBirthdate.error = """Digite uma data valida, no formato 'dd/mm/aaaa'"""
            return false
        }

    }
    private fun formatarIdade(dataDeNascimento : String): String{
        val ano: String  = dataDeNascimento.substring(6,10)
        val dia : String = dataDeNascimento.substring(0,2)
        val mes: String = dataDeNascimento.substring(3,5)

        return  ano + "-" + mes + "-"+  dia
    }
        fun validarEmail(): Boolean {
            val expressaoRegular = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            if (!expressaoRegular.matches(email)){
                binding.etEmail.error = "digite um email valido"
                return false
            }
            return true
        }


    private fun validarSenha(): Boolean {
        when {
            !senha.equals(senhaConfirmada)-> {
                binding.etPassword.error =
                    "Sua senha está diferente nos campos Senha e Confirme sua senha"

                return false
            }
            senha.length < 8 -> {
                binding.etPassword.error = "Sua senha precisa ter no minimo 8 digitos"
                return false
            }
            else -> return true
        }
    }


    private fun getAllUsers(): Boolean {
        var   result : Boolean = true
        retrofit
            .create(UsuarioService::class.java)
            .getAllUsers().enqueue(object : Callback<List<Usuario>> {
                override fun onResponse(
                    call: Call<List<Usuario>>,
                    response: Response<List<Usuario>>
                ) {
                    response.body()?.forEach {
                        if (it.email == email) {
                            val mensagem: String = "Ja existe um usuario com esse email"
                            Toast.makeText(applicationContext, mensagem, Toast.LENGTH_LONG).show()
                            result = false

                        }
                    }
                }

                override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                    print(t)
                }

            })
        return true

    }
    fun cadastrarApi(){
        val usuario: UsuarioCadastro  = UsuarioCadastro(USUARIO.id!!,email,nome,senha,formatarIdade(dataDeNascimento))
        retrofit
            .create(UsuarioService::class.java)
            .cadastrar(usuario).enqueue(object : Callback<UsuarioCadastro> {
                override fun onResponse(
                    call: Call<UsuarioCadastro>,
                    response: Response<UsuarioCadastro>
                ) {
                    if (response.isSuccessful()) {
                        GoToSignInPage()
                    }
                }

                override fun onFailure(call: Call<UsuarioCadastro>, t: Throwable) {
                    println(t)
                }



            })
    }

    private fun GoToSignInPage() {
        val signInPage = Intent(this, Login::class.java)
        startActivity(signInPage)
    }
}