package service

import models.Usuario
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {

    @GET("/usuarios")
    fun getAllUsers(): retrofit2.Call<List<Usuario>>

    @POST("/usuarios")
    fun cadastrar(
        usuario: Usuario
    ): retrofit2.Call<Usuario>

    @PUT("/usuarios/login/{email}/{senha}")
    fun login(
        @Path("email") email: String, @Path("senha") senha: String
    ): Call<Usuario>
}