package service

import com.example.saveup.NovoObjetivo
import models.Despesa
import models.Objetivo
import models.ObjetivoEditado
import models.Usuario
import retrofit2.Call
import retrofit2.http.*

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

    @PUT("/objetivos/{idUsuario}/{idObjetivo}")
    fun atualizaDespesa(
        @Path("idUsuario") idUsuario: Int,
        @Path("idObjetivo") idObjetivo: Int,
        @Body objetivo: ObjetivoEditado
    ): retrofit2.Call<Objetivo>
}