package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView

class ObjetivoExpandidoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objetivo_expandido)

        val titulo = intent.getStringExtra("titulo")
        val descricao = intent.getStringExtra("descricao")
        val valorObjetivo = intent.getDoubleExtra("valorObjetivo", 0.0)
        val valorAtual = intent.getDoubleExtra("valorAtual", 0.0)
        val progresso = intent.getIntExtra("progresso", 50)

        val tvTitulo = findViewById<TextView>(R.id.tvTituloObjetivo)
        val tvDescricao = findViewById<TextView>(R.id.tvDescricaoObjetivo)
        val tvValorObjetivo = findViewById<TextView>(R.id.tvValorObjetivo)
        val tvValorAtual = findViewById<TextView>(R.id.tvValorAtualObjetivo)
        val progressBar = findViewById<ProgressBar?>(R.id.progressBarObjetivo)

        tvTitulo.text = titulo
        tvDescricao.text = descricao
        tvValorObjetivo.text = valorObjetivo.toString()
        tvValorAtual.text = valorAtual.toString()
        progressBar.progress = progresso
    }
}