package com.example.saveup.objetivo

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.saveup.USUARIO
import models.Despesa
import models.Objetivo
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.ObjetivoService

class ObjetivoCardViewModel: ViewModel() {
    private val retrofit = Rest.getInstance()

    val allObjetivos: MutableLiveData<List<Objetivo>> = MutableLiveData()

    fun getAllObjetivosByIdUsuario(idUSUARIO:Int) {
        val objetivoService = retrofit.create(ObjetivoService::class.java)
        val call = objetivoService.getAllObjetivoByIdUsuario(idUSUARIO)

        call.enqueue(object : Callback<List<Objetivo>>{
            override fun onResponse(
                call: Call<List<Objetivo>>,
                response: Response<List<Objetivo>>
            ) {
                if (response.isSuccessful){
                    allObjetivos.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Objetivo>>, t: Throwable) {
            }

        })
    }

    fun deleteObjetivoByIdObjetivo(idObjetivo:Int) {

    }
}