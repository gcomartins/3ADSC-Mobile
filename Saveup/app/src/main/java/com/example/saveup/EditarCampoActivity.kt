package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.saveup.databinding.ActivityEditarCampoBinding
import rest.Rest

class EditarCampoActivity : AppCompatActivity() {

    private val retrofit = Rest.getInstance()

    private lateinit var binding: ActivityEditarCampoBinding

    private val tvCampoAntigo: TextView by lazy { binding.tvCampoAntigo }
    private val etCampoAntigo: TextView by lazy { binding.etCampoAntigo }
    private val tvCampoNovo: TextView by lazy { binding.tvCampoNovo }
    private val etCampoNovo: TextView by lazy { binding.etCampoNovo }
    private val btnSalvar: Button by lazy { binding.btSalvar }
    private val btnCancelar: Button by lazy { binding.btCancelar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarCampoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val atributo = intent.getStringExtra("atributo")
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
            verificarCampos()
            alterarCampo()
        }
    }

    private fun verificarCampos() {
        if (etCampoNovo.text.toString().isEmpty()) {
            etCampoNovo.error = "Campo obrigatório"
            return
        }
    }

    private fun alterarCampo() {

    }
}
