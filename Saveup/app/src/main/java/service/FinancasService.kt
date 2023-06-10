package service

import models.Despesa
import models.Receita
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

 interface FinancasService {

     @GET("/despesas/{id}")
     fun getAllDespesasById(@Path("id") id: Int): retrofit2.Call<List<Despesa>>

     @GET("/receitas/{id}")
     fun getAllReceitasById(@Path("id") id: Int): retrofit2.Call<List<Receita>>

     @GET("/usuarios/historicoReceitasMensal/{id}")
     fun getReceitasMensalById(@Path("id") id: Int): retrofit2.Call<List<Receita>>

     @GET("/usuarios/historicoDespesasMensal/{id}")
     fun getDespesasMensalById(@Path("id") id: Int): retrofit2.Call<List<Despesa>>

     @POST("/despesas/{idUsuario}")
     fun criaDespesa(
         @Path("idUsuario") idUsuario: Int,
         @Body despesa: Despesa
     ): retrofit2.Call<Despesa>

     @POST("/receitas/{id}")
     fun criaReceita(
         @Path("id") id: Int,
         @Body receita: Receita
     ): retrofit2.Call<Receita>

     @DELETE("/despesas/{idUsuario}/{idDespesa}")
     fun deletaDespesa(
         @Path("idUsuario") idUsuario: Int,
         @Path("idDespesa") idDespesa: Int
     ): retrofit2.Call<Despesa>

     @PUT("/despesas/{idUsuario}/{idDespesa}")
     fun atualizaDespesa(
         @Path("idUsuario") idUsuario: Int,
         @Path("idDespesa") idDespesa: Int,
         @Body despesa: Despesa
     ): retrofit2.Call<Despesa>
 }