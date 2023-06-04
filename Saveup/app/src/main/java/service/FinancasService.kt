package service

import models.Despesa
import models.Receita
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

     @POST("/despesas/{id}")
     fun criaDespesa(
         @Path("id") id: Int,
         @Body despesa: Despesa
     ): retrofit2.Call<Despesa>

     @POST("/receitas/{id}")
     fun criaReceita(
         @Path("id") id: Int,
         @Body receita: Receita
     ): retrofit2.Call<Receita>
}