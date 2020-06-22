package com.accentrs.iofferbh.retrofit

import com.accentrs.apilibrary.utils.Constants.BASE_URL2
import com.accentrs.apilibrary.utils.Constants.BASE_URL_DE
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceGenerator {
    fun createService(endPoint: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL2 + endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val okHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    // Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $
    var gson = GsonBuilder()
            .setLenient()
            .create()

    @JvmStatic
    fun createService(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }

    @JvmStatic
    fun createServiceDe(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL_DE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
    }
}
