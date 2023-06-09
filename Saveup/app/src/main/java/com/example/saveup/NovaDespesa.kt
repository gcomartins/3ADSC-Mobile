package com.example.saveup

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
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

class NovaDespesa : AppCompatActivity() {
    private lateinit var binding: ActivityNovaDespesaBinding
    private val retrofit = Rest.getInstance()
    private val etTitulo: EditText by lazy { binding.titulo }
    private val etDescricao: EditText by lazy { binding.descricao }
    private val etCategoria: EditText by lazy { binding.categoria }
    private val etValor: EditText by lazy { binding.valor }
    private val etData : EditText by lazy { binding.data }
    private val btAdicionar : Button by lazy { binding.adicionar }
    private val switch: Switch by lazy { binding.switchButton }
    private val handler = Handler()

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
        USUARIO.id = 1
        var financa: FinancaCriacao? = getFinanca(view)
        if (financa != null) {
                isDespesaValue(
                    Financa(
                        financa.nome,
                        financa.descricao,
                        financa.valor,
                        financa.data,
                        financa.categoria,
                        USUARIO.id!!
                    )
                )
            }
    }

    private fun getFinanca(view: View): FinancaCriacao? {
        val financa : FinancaCriacao? = checkAll()
        return financa
    }

    private fun formatarData(): String {
        val partes = etData.text.toString().split("/")
        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
        return dataFormatada

    }

    fun checkAll(): FinancaCriacao?{
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
                baseContext, "Insira o valor",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if (etData.text.toString().isNullOrBlank()) {
            Toast.makeText(
                baseContext, "Insira a data",
                Toast.LENGTH_SHORT
            ).show()
            isChecked = false
        }
        if(isChecked) {
            return FinancaCriacao(
                etTitulo.text.toString(),
                etDescricao.text.toString(),
                etValor.text.toString().toDouble(),
                formatarData(),
                etCategoria.text.toString(),
            )
        }
        return null
    }
    private fun isDespesaValue(financa: Financa){
        if (isReceita) {
            cadastrarReceita(
                Receita(
                    financa.nome,
                    financa.descricao,
                    financa.valor,
                    formatarData(),
                    financa.categoria,
                    USUARIO.id!!,
                    1
                )
            )
        }
        else {
            cadastrarDespesa(
                Despesa(
                    financa.nome,
                    financa.descricao,
                    financa.valor,
                    formatarData(),
                    financa.categoria,
                    1,
                    USUARIO.id!!
                )
            )
        }
        executarAcaoComDelay()
    }
    private fun limparEditTexts() {
        etTitulo.text.clear()
        etDescricao.text.clear()
        etCategoria.text.clear()
        etValor.text.clear()
        etData.text.clear()
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