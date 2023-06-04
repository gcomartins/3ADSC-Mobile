package service

import models.Despesa
import models.Receita
import retrofit2.http.GET
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
}