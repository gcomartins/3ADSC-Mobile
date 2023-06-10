package com.example.saveup

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveup.databinding.ActivityEditarCampoBinding
import models.UsuarioCadastro
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.UsuarioService
import java.io.IOException

class EditarCampoActivity : AppCompatActivity() {

    private val retrofit = Rest.getInstance()

    private lateinit var binding: ActivityEditarCampoBinding

    private val tvCampoAntigo: TextView by lazy { binding.tvCampoAntigo }
    private val etCampoAntigo: TextView by lazy { binding.etCampoAntigo }
    private val tvCampoNovo: TextView by lazy { binding.tvCampoNovo }
    private val etCampoNovo: TextView by lazy { binding.etCampoNovo }
    private val btnSalvar: Button by lazy { binding.btSalvar }
    private val btnCancelar: Button by lazy { binding.btCancelar }
    private val etSenhaAntiga: EditText by lazy { binding.etSenha }
    private lateinit var atributo : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarCampoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        atributo = intent.getStringExtra("atributo") ?: ""
        val valor = intent.getStringExtra("valor")
        val isObscured = intent.getBooleanExtra("isObscured", false)

        tvCampoAntigo.text = "$atributo antigo"
        etCampoAntigo.text = valor
        etCampoAntigo.isEnabled = false
        if (isObscured) {
            etCampoAntigo.inputType = 129 // 129 = textPassword
        }

        tvCampoNovo.text = "$atributo novo"
        etCampoNovo.hint = "Digite o novo $atributo"

        btnSalvar.setOnClickListener {
            atualizarUsuario()
        }
    }


    private fun verificarCampos(): Boolean {
        var verificado: Boolean = true
        // colocar ifs assim do erro
        if (etCampoNovo.text.toString().isEmpty()) {
            etCampoNovo.error = "Campo obrigatório"
            verificado = false
        }
        return verificado
    }


    fun atualizarUsuario() {
        val dado: String = etCampoNovo.text.toString()
        val usuario = when (atributo) {
            "E-mail" -> UsuarioCadastro(
                id = USUARIO.id!!,
                email = dado,
                nome = USUARIO.nome!!,
                senha = USUARIO.senha!!,
                dataNascimento = ""
            )
            "Nome" -> UsuarioCadastro(
                id = USUARIO.id!!,
                email = USUARIO.email!!,
                nome = dado,
                senha = USUARIO.senha!!,
                dataNascimento = ""
            )
            "Senha" -> UsuarioCadastro(
                id = USUARIO.id!!,
                email = USUARIO.email!!,
                nome = USUARIO.nome!!,
                senha = dado,
                dataNascimento = ""
            )
            "Data de Nascimento" -> UsuarioCadastro(
                id = USUARIO.id!!,
                email = USUARIO.email!!,
                nome = USUARIO.nome!!,
                senha = USUARIO.senha!!,
                dataNascimento = formatarData(dado)
            )
            else -> UsuarioCadastro(
                id = USUARIO.id!!,
                email = USUARIO.email!!,
                nome = USUARIO.nome!!,
                senha = USUARIO.senha!!,
                dataNascimento = ""
            )
        }

        if (verificarCampos()) {
            requestApi(usuario)
        }
    }

    private fun formatarData(data: String): String {
        val partes = data.split("/")
        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
        return dataFormatada
    }

    fun requestApi(usuario: UsuarioCadastro) {
        retrofit.create(UsuarioService::class.java).atualizarUsuario(
            senhaAntiga =
            etSenhaAntiga.text.toString(),
            USUARIO.email!!,
            usuario
        ).enqueue(object : Callback<UsuarioCadastro> {
            override fun onResponse(
                call: Call<UsuarioCadastro>,
                response: Response<UsuarioCadastro>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext, "${atributo} atualizado com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(applicationContext, usuario.toString(), Toast.LENGTH_SHORT)
                        .show()

                }

            }

            override fun onFailure(call: Call<UsuarioCadastro>, t: Throwable) {

                // Aqui você pode tratar a resposta de erro da API de acordo com suas necessidades
                if (t is IOException) {
                    Toast.makeText(
                        applicationContext,
                        "Erro de conexão de rede",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Erro na resposta da API",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}
