package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EditarCampoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_campo)

        val atributo = intent.getStringExtra("atributo")
        val valor = intent.getStringExtra("valor")
        val isObscured = intent.getBooleanExtra("isObscured", false)

        val campoAntigo = findViewById<TextView>(R.id.tvCampoAntigo)
        campoAntigo.text = "$atributo antigo"


        val etCampoAntigo = findViewById<TextView>(R.id.etCampoAntigo)
        etCampoAntigo.text = "$valor"
        etCampoAntigo.isEnabled = false
        if(isObscured) {
            etCampoAntigo.inputType = 129 // 129 = textPassword
        }

        val campoNovo = findViewById<TextView>(R.id.tvCampoNovo)
        campoNovo.text = "$atributo novo"

        val etCampoNovo = findViewById<TextView>(R.id.etCampoNovo)
        etCampoNovo.hint = "Digite o novo $atributo"
    }
}