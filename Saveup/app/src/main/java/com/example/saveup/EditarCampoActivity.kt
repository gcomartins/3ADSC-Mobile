package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EditarCampoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_campo)

        val atributo = intent.getStringExtra("atributo")

        val campoAntigo = findViewById<TextView>(R.id.tvCampoAntigo)
        campoAntigo.text = "$atributo antigo"

        val campoNovo = findViewById<TextView>(R.id.tvCampoNovo)
        campoNovo.text = "$atributo novo"
    }
}