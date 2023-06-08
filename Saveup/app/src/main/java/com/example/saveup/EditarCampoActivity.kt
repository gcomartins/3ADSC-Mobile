package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.saveup.databinding.ActivityEditarCampoBinding

class EditarCampoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarCampoBinding

    private val tvCampoAntigo: TextView by lazy { binding.tvCampoAntigo }
    private val etCampoAntigo: TextView by lazy { binding.etCampoAntigo }
    private val tvCampoNovo: TextView by lazy { binding.tvCampoNovo }
    private val etCampoNovo: TextView by lazy { binding.etCampoNovo }

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
    }
}
