package com.example.saveup.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saveup.USUARIO
import models.Despesa
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService

class DashboardViewModel: ViewModel() {
    private val retrofit = Rest.getInstance()
    private val financasService = retrofit.create(FinancasService::class.java)

    val despesasList: MutableLiveData<List<Despesa>> = MutableLiveData(emptyList())
    val despesasGrafico: MutableLiveData<MutableMap<String, Double>> = MutableLiveData( mutableMapOf())

    fun getDespesasList(){
        financasService.getAllDespesasById(USUARIO.id!!).enqueue(object: Callback<List<Despesa>> {
            override fun onResponse(call: Call<List<Despesa>>, response: Response<List<Despesa>>) {
                if(response.isSuccessful){
                    val responseList: List<Despesa> = response.body() ?: emptyList()
                    despesasList.value = responseList
                    val mapResponse: MutableMap<String, Double> = mutableMapOf()
                    for (despesa in responseList) {
                        val categoria = despesa.categoria
                        val valor = despesa.valor


                        if (mapResponse.containsKey(categoria)) {
                            val valorExistente = mapResponse[categoria] ?: 0.0
                            mapResponse[categoria] = valorExistente + valor
                        } else {
                            mapResponse[categoria] = valor
                        }
                    }
                    despesasGrafico.value = mapResponse
                }
            }

            override fun onFailure(call: Call<List<Despesa>>, t: Throwable) {
                Log.e("ERRO", t.message.toString())
            }
        })
    }
}