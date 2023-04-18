package service

import models.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioService {

    @GET("/usuarios")
    fun getAllUsers(): Call<List<Usuario>>

    @POST("/usuarios")
    fun cadastrar(
        usuario: Usuario
    ): Call<Usuario>
}