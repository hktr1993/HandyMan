package code.challenge.handyman

import code.challenge.handyman.models.HandyMan
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


public interface CeleroService {

    @GET("celerocustomers.json")
    fun listMen(): Call<List<HandyMan>>

    companion object {
        fun create() : CeleroService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hulet.tech/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CeleroService::class.java)
        }

    }

}