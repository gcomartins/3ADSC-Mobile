package com.example.saveup.resumo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saveup.USUARIO
import models.Despesa
import models.Receita
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService

class DashboardResumoViewModel: ViewModel() {
    private val retrofit = Rest.getInstance()

    val allDespesas: MutableLiveData<List<ValorGrafico>> = MutableLiveData()
    val allReceitas: MutableLiveData<List<ValorGrafico>> = MutableLiveData()
    val saldoPorMes: MutableLiveData<List<ValorGrafico>> = MutableLiveData()

    fun getAllDespesasByIdUsuario(idUsuario: Int) {
        val despesaService = retrofit.create(FinancasService::class.java)
        val call = despesaService.getAllDespesasById(idUsuario)

        call.enqueue(object : Callback<List<Despesa>> {
            override fun onResponse(
                call: Call<List<Despesa>>,
                response: Response<List<Despesa>>
            ) {
                if (response.isSuccessful){
                    allDespesas.value = response.body()?.map { despesa ->
                        ValorGrafico(
                            valor = despesa.valor,
                            mes = despesa.data.subSequence(5, 7).toString().toInt(),
                            ano = despesa.data.subSequence(0, 4).toString().toInt()
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<Despesa>>, t: Throwable) {

            }

        })
    }

    fun getAllReceitasByIdUsuario(idUsuario: Int) {
        val receitaService = retrofit.create(FinancasService::class.java)
        val call = receitaService.getAllReceitasById(idUsuario)

        call.enqueue(object : Callback<List<Receita>> {
            override fun onResponse(
                call: Call<List<Receita>>,
                response: Response<List<Receita>>
            ) {
                if (response.isSuccessful){
                    allReceitas.value = response.body()?.map {receita ->
                        ValorGrafico(
                            valor = receita.valor,
                            mes = receita.data.subSequence(5, 7).toString().toInt(),
                            ano = receita.data.subSequence(0, 4).toString().toInt()
                        )
                    }
                }
            }

            override fun onFailure(call: Call<List<Receita>>, t: Throwable) {

            }

        })
    }

    fun criarDespesa(
        context: Context,
        despesa: Despesa
    ){
        val despesaService = retrofit.create(FinancasService::class.java)
        val call = despesaService.criaDespesa(
            id = USUARIO.id!!,
            despesa = despesa,
        )

        call.enqueue(object : Callback<Despesa> {
            override fun onResponse(
                call: Call<Despesa>,
                response: Response<Despesa>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(context, "Despesa criada com sucesso", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Despesa>, t: Throwable) {
                Toast.makeText(context, "Erro ao criar Despesa", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun criarReceita(
        context: Context,
        receita: Receita
    ){
        val receitaService = retrofit.create(FinancasService::class.java)
        val call = receitaService.criaReceita(
            id = USUARIO.id!!,
            receita = receita,
        )

        call.enqueue(object : Callback<Receita> {
            override fun onResponse(
                call: Call<Receita>,
                response: Response<Receita>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(context, "Receita criada com sucesso", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Receita>, t: Throwable) {
                Toast.makeText(context, "Erro ao criar Receita", Toast.LENGTH_SHORT).show()
            }

        })
    }
}