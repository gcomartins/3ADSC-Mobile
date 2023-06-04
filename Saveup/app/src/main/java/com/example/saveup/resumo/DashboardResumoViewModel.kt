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

    val allDespesas: MutableLiveData<List<Despesa>> = MutableLiveData()
    val allReceitas: MutableLiveData<List<Receita>> = MutableLiveData()
    val saldoPorMes: MutableLiveData<List<DinheiroGuardado>> = MutableLiveData()

    fun getAllDespesasByIdUsuario(idUsuario: Int) {
        val despesaService = retrofit.create(FinancasService::class.java)
        val call = despesaService.getAllDespesasById(idUsuario)

        call.enqueue(object : Callback<List<Despesa>> {
            override fun onResponse(
                call: Call<List<Despesa>>,
                response: Response<List<Despesa>>
            ) {
                if (response.isSuccessful){
                    allDespesas.value = response.body() ?: listOf()
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
                    allReceitas.value = response.body() ?: listOf()
                }
            }

            override fun onFailure(call: Call<List<Receita>>, t: Throwable) {

            }

        })
    }

    fun criarDespesa(context: Context){
        val despesaService = retrofit.create(FinancasService::class.java)
        val call = despesaService.criaDespesa(
            id = USUARIO.id!!,
            despesa = Despesa(
                1,
                "Uber",
                "Uber",
                20.0,
                "2021-10-10",
                "Uber",
                5,
                true,
                USUARIO.id!!
            )
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

    fun criarReceita(context: Context){
        val receitaService = retrofit.create(FinancasService::class.java)
        val call = receitaService.criaReceita(
            id = 1,
            receita = Receita(
                1,
                "Salário",
                "Salário",
                2000.0,
                "2021-10-10",
                "Salário",
                5,
                true,
                1
            )
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