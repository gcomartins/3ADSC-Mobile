package com.example.saveup

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveup.databinding.ActivityNovaDespesaBinding
import models.DespesaCriacao
import rest.Rest
import java.util.*

class NovaDespesa : AppCompatActivity() {
    private lateinit var binding: ActivityNovaDespesaBinding
    private val retrofit = Rest.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaDespesaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.adicionar.setOnClickListener { view -> cadastrarGasto(view) }
    }
    private fun cadastrarGasto(view: View){
        var despesa: DespesaCriacao? = getDespesa(view)
        Toast.makeText(baseContext, despesa.toString(), Toast.LENGTH_SHORT).show()

    }

    private fun getDespesa(view: View): DespesaCriacao? {
        val nome: String?= binding.titulo.text?.toString()
        val descricao: String? = binding.descricao.text?.toString()
        val categoria: String? =  binding.categoria.text?.toString()
        val valor: Double? = binding.valor.text?.toString()?.toDouble()
        val qtdParcelas: Int? =  binding.qtdParcelas.text?.toString()?.toInt()
        val data: Date? =  transformandoEmData()
        val despesa = checkAll(nome, descricao, valor,data,categoria,qtdParcelas,this.baseContext)

        return despesa
    }

    private fun transformandoEmData(): Date? {
        val formatoData = SimpleDateFormat("dd/MM/yyyy")
        val dataString: String?  =  binding.data.toString()
        if (!dataString.isNullOrBlank()){
        val data = formatoData.parse(dataString)
        return data
        }
        return null
    }
    fun checkAll(nome: String?, descricao: String?, valor: Double?,data: Date?, categoria: String?,
                 qtdParcelas: Int?,  baseContext: Context): DespesaCriacao?{
        var isChecked: Boolean = true
        if (nome.isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Preencha o valor de Titulo",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (descricao.isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Preencha o valor de Descrição",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if ( valor == null) {
            Toast.makeText(
                baseContext, "Insira o valor da sua despesa",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (data.toString().isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Insira a data da sua despesa",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if(isChecked) return DespesaCriacao(nome,descricao,valor,data,categoria, qtdParcelas)
        return null
    }
}