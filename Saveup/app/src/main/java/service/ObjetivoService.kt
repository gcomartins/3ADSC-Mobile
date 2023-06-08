package service

import com.example.saveup.NovoObjetivo
import com.example.saveup.Objetivo
import models.Despesa
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ObjetivoService {

    @POST("/objetivos/{idUsuario}")
    fun criaObjetivo(
        @Path("idUsuario")  id: Int,
        @Body despesa: NovoObjetivo
    ): retrofit2.Call<NovoObjetivo>
}