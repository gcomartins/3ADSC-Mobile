package com.example.saveup

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.saveup.databinding.ActivityNovaDespesaBinding
import models.Despesa
import models.Financa
import models.FinancaCriacao
import models.Receita
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService
import java.io.IOException
import java.time.LocalDate

class NovaDespesa : AppCompatActivity() {
    private lateinit var binding: ActivityNovaDespesaBinding
    private val retrofit = Rest.getInstance()
    private val etTitulo: EditText by lazy { binding.titulo }
    private val etDescricao: EditText by lazy { binding.descricao }
    private val etCategoria: Spinner by lazy { binding.spinnerCategoria }
    private val etValor: EditText by lazy { binding.valor }
    private val btAdicionar : Button by lazy { binding.adicionar }
    private val switch: Switch by lazy { binding.switchButton }
    private val handler = Handler()
    private var dataSelecionada = ""
    private val datePicker by lazy { binding.datePicker }
    var isReceita : Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNovaDespesaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btAdicionar.setOnClickListener { view -> cadastrarFinanca(view) }
        switch.setOnCheckedChangeListener { _, isChecked ->
            isReceita = isChecked
            layoutReceita()
        }

        dataSelecionada = LocalDate.now().toString()

        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth) { view, year, monthOfYear, dayOfMonth ->
            // Aqui você pode capturar a data selecionada e fazer o que precisar com ela

            // Formate os números abaixo de 10 com zero à esquerda
            val dayFormatted = String.format("%02d", dayOfMonth)
            val monthFormatted = String.format("%02d", monthOfYear + 1)
            val yearFormatted = String.format("%02d", year)

            dataSelecionada = "$yearFormatted-$monthFormatted-$dayFormatted"
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun layoutReceita(){
            mudarBotao()
            mudarSwtichColor()
    }
    private fun mudarSwtichColor(){
        val contextThemeWrapper = ContextThemeWrapper(this, R.style.RedSwitch)
        val styledSwitch = Switch(contextThemeWrapper)
        if (isReceita){
            styledSwitch.thumbTintList = AppCompatResources.getColorStateList(this, R.color.primary)
            styledSwitch.trackTintList = AppCompatResources.getColorStateList(this, R.color.cinza_switch)
        }else{
            styledSwitch.thumbTintList = AppCompatResources.getColorStateList(this, R.color.cinza_switch_button)
            styledSwitch.trackTintList = AppCompatResources.getColorStateList(this, R.color.cinza_switch)
        }


            binding.switchButton.thumbDrawable = styledSwitch.thumbDrawable
            binding.switchButton.trackDrawable = styledSwitch.trackDrawable
        }

    private fun mudarBotao(){
        val button = btAdicionar
        if (isReceita) {
            val stringAdicionarReceita = resources.getString(R.string.adicionar_receita)
            val color = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.primary))
            button.backgroundTintList = color
            btAdicionar.text = stringAdicionarReceita
        }else{
            val stringAdicionarReceita = resources.getString(R.string.adicionar_gasto)

            val color = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            button.backgroundTintList = color
            btAdicionar.text = stringAdicionarReceita
        }

    }
    private fun cadastrarFinanca(view: View) {
        var financa: FinancaCriacao? = getFinanca(view)
        if (financa != null) {
                isDespesaValue(
                    Financa(
                        nome = financa.nome,
                        descricao = financa.descricao,
                        valor = financa.valor,
                        data = financa.data,
                        categoria = financa.categoria,
                        fkUsuario = USUARIO.id!!,
                        codigo = null,
                    )
                )
            }
    }

    private fun getFinanca(view: View): FinancaCriacao? {
        val financa : FinancaCriacao? = checkAll()
        return financa
    }

//    private fun formatarData(): String {
//        val partes = dataSelecionada.split("/")
//        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
//        return dataFormatada
//    }

    fun checkAll(): FinancaCriacao?{
        var isChecked: Boolean = true
        if (etTitulo.text.isNullOrBlank()) {
            etTitulo.error = "Campo obrigatório"
            isChecked = false
        }
        if (etDescricao.text.isNullOrBlank()) {
            etDescricao.error = "Campo obrigatório"
            isChecked = false
        }
        if (etCategoria.selectedItem.toString().isNullOrBlank()) {
            isChecked = false
        }
        if ( etValor.text.isNullOrBlank()) {
            etValor.error = "Campo obrigatório"
            isChecked = false
        }
        if(isChecked) {
            return FinancaCriacao(
                etTitulo.text.toString(),
                etDescricao.text.toString(),
                etValor.text.toString().toDouble(),
                dataSelecionada,
                etCategoria.selectedItem.toString()
            )
        }
        return null
    }
    private fun isDespesaValue(financa: Financa){
        if (isReceita) {
            cadastrarReceita(
                Receita(
                    nome = financa.nome,
                    descricao = financa.descricao,
                    valor = financa.valor,
                    data = dataSelecionada,
                    categoria = financa.categoria,
                    fkUsuario = USUARIO.id!!,
                    frequencia = 1,
                    codigo = null
                )
            )
        }
        else {
            cadastrarDespesa(
                Despesa(
                    nome = financa.nome,
                    descricao = financa.descricao,
                    valor = financa.valor,
                    data = dataSelecionada,
                    categoria = financa.categoria,
                    qtdParcelas = 1,
                    fkUsuario = USUARIO.id!!,
                    codigo = null
                )
            )
        }
        executarAcaoComDelay()
    }


    private fun limparEditTexts() {
        etTitulo.text.clear()
        etDescricao.text.clear()
        etValor.text.clear()
    }
    private fun executarAcaoComDelay() {
        val delayMillis = 3000 // 2 segundos

        handler.postDelayed({
            // Ação a ser executada após o atraso
            limparEditTexts()
        }, delayMillis.toLong())
    }



    //requests api despesa
    private fun cadastrarDespesa(despesa: Despesa){
        retrofit.
        create(FinancasService::class.java).
        criaDespesa(USUARIO.id!!, despesa).
        enqueue(object : Callback<Despesa> {
            override fun onResponse(call: Call<Despesa>, response: Response<Despesa>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext, "despesa criada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(applicationContext, despesa.toString(), Toast.LENGTH_SHORT).show()

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

    //funcoes de receita
    private fun cadastrarReceita(receita: Receita){
        retrofit.
        create(FinancasService::class.java).
        criaReceita(USUARIO.id!!, receita).
        enqueue(object : Callback<Receita> {
            override fun onResponse(call: Call<Receita>, response: Response<Receita>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        baseContext, "Receita criada com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(applicationContext, response.code().toString(), Toast.LENGTH_SHORT).show()

                }

            }

            override fun onFailure(call: Call<Receita>, t: Throwable) {

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