package com.example.saveup

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.saveup.databinding.ActivityNovaDespesaBinding
import models.Despesa
import models.DespesaCriacao
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService
import java.io.IOException

class NovaDespesa : AppCompatActivity() {
    private lateinit var binding: ActivityNovaDespesaBinding
    private val retrofit = Rest.getInstance()
    private val etTitulo: EditText by lazy { binding.titulo }
    private val etDescricao: EditText by lazy { binding.descricao }
    private val etCategoria: EditText by lazy { binding.categoria }
    private val etValor: EditText by lazy { binding.valor }
    private val etQtdParcelas: EditText by lazy { binding.qtdParcelas }
    private val etData : EditText by lazy { binding.data }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNovaDespesaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.adicionar.setOnClickListener { view -> cadastrarGasto(view) }
    }
    private fun cadastrarGasto(view: View){
        USUARIO.id = 1
        var despesa: DespesaCriacao? = getDespesa(view)
        if (despesa != null) cadastrarGastoBanco(
            Despesa(1,despesa.nome,
                despesa.descricao,
                despesa.valor,
                despesa.data,
                despesa.categoria,
                despesa.qtdParcelas,
                false,
                USUARIO.id!!
            ))
    }

    private fun getDespesa(view: View): DespesaCriacao? {
        val despesa : DespesaCriacao? = checkAll()
        return despesa
    }

    private fun formatarData(): String {
        val partes = etData.text.toString().split("/")
        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
        return dataFormatada

    }

    fun checkAll(): DespesaCriacao?{
        var isChecked: Boolean = true
        if (etTitulo.text.isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Preencha o valor de Titulo",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (etDescricao.text.isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Preencha o valor de Descrição",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if ( etValor.text.isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Insira o valor da sua despesa",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (etData.text.toString().isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Insira a data da sua despesa",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (etQtdParcelas.text.toString().isNullOrBlank()) {
            val editable: Editable = Editable.Factory.getInstance().newEditable("1")
            etQtdParcelas.text = editable
        }
        if(isChecked) {
            return DespesaCriacao(
                etTitulo.text.toString(),
                etDescricao.text.toString(),
                etValor.text.toString().toDouble(),
                formatarData(),
                etCategoria.text.toString(),
                etQtdParcelas.text.toString().toInt()
            )
        }
        return null
    }

    private fun cadastrarGastoBanco(despesa: Despesa){
        retrofit.
        create(FinancasService::class.java).
        criaDespesa(USUARIO.id!!,despesa).
        enqueue(object : Callback<Despesa> {
            override fun onResponse(call: Call<Despesa>, response: Response<Despesa>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext, "despesa criada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<Despesa>, t: Throwable) {

                // Aqui você pode tratar a resposta de erro da API de acordo com suas necessidades
                if (t is IOException) {
                    Toast.makeText(applicationContext, "Erro de conexão de rede", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Erro na resposta da API", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}