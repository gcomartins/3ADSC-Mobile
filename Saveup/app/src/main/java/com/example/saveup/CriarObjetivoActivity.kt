package com.example.saveup

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.saveup.databinding.ActivityCriarObjetivoBinding
import models.Despesa
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService
import service.ObjetivoService
import java.io.IOException
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

class CriarObjetivoActivity : AppCompatActivity() {

    private val retrofit = Rest.getInstance()
    private val binding by lazy { ActivityCriarObjetivoBinding.inflate(layoutInflater) }

    private val etTitulo by lazy { binding.editTextTitulo }
    private val etDescricao by lazy { binding.editTextDescricao }
    private val etValor by lazy { binding.editTextMetaValor }
    private val datePicker by lazy { binding.datePicker }
    private val etCategoria by lazy { binding.spinnerCategoria }
    private val etValorAtual by lazy { binding.editTextValorAtual }

    private var dataSelecionada = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth) { view, year, monthOfYear, dayOfMonth ->
            // Aqui você pode capturar a data selecionada e fazer o que precisar com ela

            // Formate os números abaixo de 10 com zero à esquerda
            val dayFormatted = String.format("%02d", dayOfMonth)
            val monthFormatted = String.format("%02d", monthOfYear + 1)
            val yearFormatted = String.format("%02d", year)

            dataSelecionada = "$dayFormatted/$monthFormatted/$yearFormatted"
        }

        binding.buttonCriarObjetivo.setOnClickListener {
            val nome = etTitulo.text.toString()
            val descricao = etDescricao.text.toString()
            val valor = etValor.text.toString().toDoubleOrNull()
            val categoria = etCategoria.selectedItem.toString()
            val valorAtual = etValorAtual.text.toString().toDoubleOrNull()


            if (valor != null && valorAtual != null) {
                val novoObjetivo = NovoObjetivo(
                    nome = nome,
                    descricao = descricao,
                    valor = valor,
                    data = LocalDate.now().toString(),
                    categoria = categoria,
                    valorAtual = valorAtual ?: 0.0,
                    fkUsuario = USUARIO.id!!,
                    dataFinal = formatarData()
                )

                criarObjetivo(novoObjetivo)
                finish()
            } else {
                Toast.makeText(this, "Valores inválidos", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun formatarData(): String {
        val partes = dataSelecionada.split("/")
        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
        return dataFormatada

    }

    private fun criarObjetivo(novoObjetivo: NovoObjetivo){
        retrofit.
        create(ObjetivoService::class.java).
        criaObjetivo(USUARIO.id!!, novoObjetivo).
        enqueue(object : Callback<NovoObjetivo> {
            override fun onResponse(call: Call<NovoObjetivo>, response: Response<NovoObjetivo>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Objetivo criada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<NovoObjetivo>, t: Throwable) {

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

data class NovoObjetivo(
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
    val valorAtual: Double,
    val dataFinal: String,
    val fkUsuario: Int
)
