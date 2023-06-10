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

    fun getDespesasList(){
        financasService.getAllDespesasById(USUARIO.id!!).enqueue(object: Callback<List<Despesa>> {
            override fun onResponse(call: Call<List<Despesa>>, response: Response<List<Despesa>>) {
                if(response.isSuccessful){
                    despesasList.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Despesa>>, t: Throwable) {
                Log.e("ERRO", t.message.toString())
            }
        })
    }
}