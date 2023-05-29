package service

import models.Usuario
import models.UsuarioCadastro
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {

    @GET("/usuarios")
    fun getAllUsers(): retrofit2.Call<List<Usuario>>

    @POST("/usuarios")
    fun cadastrar(
       @Body usuarioCadastro: UsuarioCadastro

    ): retrofit2.Call<UsuarioCadastro>

    @PUT("/usuarios/login/{email}/{senha}")
    fun login(
        @Path("email") email: String, @Path("senha") senha: String
    ): Call<Usuario>
}