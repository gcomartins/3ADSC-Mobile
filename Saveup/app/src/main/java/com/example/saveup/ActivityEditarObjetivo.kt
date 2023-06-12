package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.saveup.databinding.ActivityEditarObjetivoBinding
import models.Objetivo
import models.ObjetivoEditado
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ObjetivoService

class ActivityEditarObjetivo : AppCompatActivity() {

    val retrofit = Rest.getInstance()
    val service = retrofit.create(ObjetivoService::class.java)

    private lateinit var binding: ActivityEditarObjetivoBinding

    val etTituloAntigo: TextView by lazy { binding.etTituloInseridoAntigo }
    val etDescricaoAntigo: TextView by lazy { binding.etDescricaoInseridoAntigo }
    val etValorInseridoAntigo: TextView by lazy { binding.etValorInseridoAntigo }
    val etTituloNovo: TextView by lazy { binding.etTituloNovo }
    val etDescricaoNovo: TextView by lazy { binding.etDescricaoNovo }
    val etValorInseridoNovo: TextView by lazy { binding.etValorNovo }

    private val btnSalvar: Button by lazy { binding.btSalvar }
    private val btnCancelar: Button by lazy { binding.btCancelar }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val titulo = intent.getStringExtra("titulo")
        val descricao = intent.getStringExtra("descricao")
        val valor = intent.getDoubleExtra("valor", 0.0)


//        val nome = intent.getStringExtra("titulo")
//        val detalhe = intent.getStringExtra("descricao")
//        val valorAtual = intent.getDoubleExtra("valorAtual", 0.0)
//
//        val tvTitulo = findViewById<TextView>(R.id.etTituloAntigo)
//        val tvDescricao = findViewById<TextView>(R.id.etDescricaoAntigo)
//        val tvValorAtual = findViewById<TextView>(R.id.etValorInseridoAntigo)
//
//        tvTitulo.text = nome
//        tvDescricao.text = detalhe
//        tvValorAtual.text = valorAtual.toString()

        etTituloNovo.text = Editable.Factory.getInstance().newEditable(titulo)
        etValorInseridoNovo.text = Editable.Factory.getInstance().newEditable(valor.toString())
        etDescricaoNovo.text = Editable.Factory.getInstance().newEditable(descricao)

        btnSalvar.setOnClickListener {
            atualizarObjetivoById(id)
            finish()
        }
        btnCancelar.setOnClickListener {
            finish()
        }

    }

    private fun atualizarObjetivoById(idObjetivo: Int) {
        val objetivo = ObjetivoEditado(
            id = idObjetivo,
            nome = etTituloNovo.text.toString(),
            descricao = etDescricaoNovo.toString(),
            valor = etValorInseridoNovo.text.toString().toDouble(),
            fkUsuario = USUARIO.id!!,
        )

        service.atualizaDespesa(USUARIO.id!!, idObjetivo, objetivo).enqueue(object :
            Callback<Objetivo> {
            override fun onResponse(call: Call<Objetivo>, response: Response<Objetivo>) {
                if (response.isSuccessful) {
                    val objetivo = response.body()
                    if (objetivo != null) {
                        Toast.makeText(
                            this@ActivityEditarObjetivo,
                            "Objetivo atualizada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Objetivo>, t: Throwable) {
                Toast.makeText(
                    this@ActivityEditarObjetivo,
                    "Erro ao atualizar Objetivo!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}