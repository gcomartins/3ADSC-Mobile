package rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    private val BASE_URL = "http://54.163.208.87:8080"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}