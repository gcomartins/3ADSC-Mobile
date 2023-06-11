package service

import com.example.saveup.NovoObjetivo
import models.Despesa
import models.Objetivo
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ObjetivoService {

    @POST("/objetivos/{idUsuario}")
    fun criaObjetivo(
        @Path("idUsuario")  id: Int,
        @Body despesa: NovoObjetivo
    ): retrofit2.Call<NovoObjetivo>

    @GET("/objetivos/{idUsuario}")
    fun getAllObjetivoByIdUsuario (@Path("idUsuario")id: Int): retrofit2.Call<List<Objetivo>>

    @DELETE("/objetivos/{idUsuario}/{idObjetivo}")
    fun deletaObjetivo(
        @Path("idUsuario") idUsuario: Int,
        @Path("idObjetivo") idDespesa: Int
    ): retrofit2.Call<Objetivo>
}